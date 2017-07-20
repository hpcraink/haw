#!/usr/bin/env bash

SERVER_IP="${SERVER_IP:-192.168.56.40}"
NETWORK="${NETWORK:-192.168.56.0}"
BROADCAST="${BROADCAST:-192.168.56.255}"

SSH_USER="${SSH_USER:-$(whoami)}"
KEY_USER="${KEY_USER:-$(whoami)}"

APP_ENV="${APP_ENV:-staging}"

#DOCKER_PULL_IMAGES=("postgres:9" "redis:3")
DOCKER_PULL_IMAGES=()
COPY_UNIT_FILES=("iptables-restore" "swap")
SSL_CERT_BASE_NAME="productionexample"

function preseed_staging() {
  cat << EOF
  STAGING SERVER (DIRECT VIRTUAL MACHINE) DIRECTIONS:
    1. Configure a static IP address directly on the VM
      $ su
      <enter password>
      $ nano /etc/network/interfaces
        [add the following lines:]
        # The host only network interface
        auto enp0s8
        iface enp0s8 inet static
        address ${1}
        netmask 255.255.255.0
        network ${NETWORK}
        broadcast ${BROADCAST}
    1.1 Set the hostname
      $ nano /etc/hostname
      edit /etc/hosts as well when a new sudo user was created
    2. Reboot the VM and ensure the Debian CD is mounted

    3. Install sudo
      $ apt-get update && apt-get install -y -q sudo
      $ apt-get upgrade
      $ rm -rf /var/lib/apt/lists/*

    4. Add the user to the sudo group
      $ adduser ${SSH_USER} sudo

    5. Run the commands in: ${0} --help
      Example:
        $ ${0} -a
EOF
}

function configure_sudo() {
  echo "Configuring passwordless sudo..."
  scp "sudo/sudoers" "${SSH_USER}@${1}:/tmp/sudoers"
  ssh -t "${SSH_USER}@${1}" bash -c "'
    sudo chmod 440 /tmp/sudoers &&
    sudo chown root:root /tmp/sudoers &&
    sudo mv /tmp/sudoers /etc
  '"
  echo "done!"
}

function add_ssh_key() {
  echo "Adding SSH key..."
  cat "$HOME/.ssh/id_rsa.pub" | ssh -t "${SSH_USER}@${1}" bash -c "'
    mkdir /home/${KEY_USER}/.ssh
    cat >> /home/${KEY_USER}/.ssh/authorized_keys
  '"
  ssh -t "${SSH_USER}@${1}" bash -c "'
    chmod 700 /home/${KEY_USER}/.ssh
    chmod 640 /home/${KEY_USER}/.ssh/authorized_keys
    sudo chown ${KEY_USER}:${KEY_USER} -R /home/${KEY_USER}/.ssh
  '"
  echo "done!"
}

function configure_secure_ssh() {
  echo "Configuing secure SSH..."
  scp "ssh/sshd_config" "${SSH_USER}@${1}:/tmp/sshd_config"
  ssh -t "${SSH_USER}@${1}" bash -c "'
    sudo chown root:root /tmp/sshd_config
    sudo mv /tmp/sshd_config /etc/ssh
    sudo systemctl restart ssh
  '"
  echo "done!"
}

function install_docker() {
  echo "Configuring Docker..."
  ssh -t "${SSH_USER}@${1}" bash -c "'
    sudo apt-get update &&
    sudo apt-get install -y curl &&
    curl -sSL https://get.docker.com/ | sh &&
    sudo rm -rf /var/lib/apt/lists/* &&
    sudo usermod -aG docker ${KEY_USER}
  '"
  echo "done!"
}

function docker_pull() {
  echo "Pulling Docker images..."
  for image in "${DOCKER_PULL_IMAGES[@]}"
  do
    ssh -t "${SSH_USER}@${1}" bash -c "'docker pull ${image}'"
  done
  echo "done!"
}

function configure_firewall() {
  echo "Configuring iptables firewall..."
  scp "iptables/rules-save" "${SSH_USER}@${1}:/tmp/rules-save"
  ssh -t "${SSH_USER}@${1}" bash -c "'
    sudo mkdir -p /var/lib/iptables
    sudo mv /tmp/rules-save /var/lib/iptables
    sudo chown root:root -R /var/lib/iptables
  '"
}

function copy_units() {
  echo "Copying systemd unit files..."
  for unit in "${COPY_UNIT_FILES[@]}"
  do
    scp "units/${unit}.service" "${SSH_USER}@${1}:/tmp/${unit}.service"
    ssh -t "${SSH_USER}@${1}" bash -c "'
      sudo mv /tmp/${unit}.service /etc/systemd/system
      sudo chown ${SSH_USER}:${SSH_USER} /etc/systemd/system/${unit}.service
    '"
  done
  echo "done!"
}

function enable_base_units() {
  echo "Enabling base systemd units..."
  for unit in "${COPY_UNIT_FILES[@]}"
  do
    ssh -t "${SSH_USER}@${1}" bash -c "'
      sudo systemctl enable ${unit}.service
      sudo systemctl start ${unit}.service
    '"
    ssh -t "${SSH_USER}@${1}" bash -c "'
      sudo systemctl restart docker
      '"
  done
  echo "done!"
}

function copy_env_config_files() {
  echo "Copying environment/config files..."
    scp "${APP_ENV}/__init__.py" "${SSH_USER}@${1}:/tmp/__init__.py"
    scp "${APP_ENV}/settings.py" "${SSH_USER}@${1}:/tmp/settings.py"
    ssh -t "${SSH_USER}@${1}" bash -c "'
      sudo mkdir -p /home/${SSH_USER}/config
      sudo mv /tmp/__init__.py /home/${SSH_USER}/config/__init__.py
      sudo mv /tmp/settings.py /home/${SSH_USER}/config/settings.py
      sudo chown ${SSH_USER}:${SSH_USER} -R /home/${SSH_USER}/config
    '"
  echo "done!"
}

function copy_ssl_certs() {
  echo "Copying SSL certificates..."
  if [[ "${APP_ENV}" == "staging" ]]; then
    scp "nginx/certs/${SSL_CERT_BASE_NAME}.crt" "${SSH_USER}@${1}:/tmp/${SSL_CERT_BASE_NAME}.crt"
    scp "nginx/certs/${SSL_CERT_BASE_NAME}.key" "${SSH_USER}@${1}:/tmp/${SSL_CERT_BASE_NAME}.key"
    scp "nginx/certs/dhparam.pem" "${SSH_USER}@${1}:/tmp/dhparam.pem"
  else
    scp "production/certs/${SSL_CERT_BASE_NAME}.crt" "${SSH_USER}@${1}:/tmp/${SSL_CERT_BASE_NAME}.crt"
    scp "production/certs/${SSL_CERT_BASE_NAME}.key" "${SSH_USER}@${1}:/tmp/${SSL_CERT_BASE_NAME}.key"
    scp "production/certs/dhparam.pem" "${SSH_USER}@${1}:/tmp/dhparam.pem"
  fi
  ssh -t "${SSH_USER}@${1}" bash -c "'
    sudo mv /tmp/${SSL_CERT_BASE_NAME}.crt /etc/ssl/certs/${SSL_CERT_BASE_NAME}.crt
    sudo mv /tmp/${SSL_CERT_BASE_NAME}.key /etc/ssl/private/${SSL_CERT_BASE_NAME}.key
    sudo mv /tmp/dhparam.pem /etc/ssl/private/dhparam.pem
    sudo chown root:root -R /etc/ssl
  '"
  echo "done!"
}

function provision_server() {
  configure_sudo
  echo "---"
  add_ssh_key
  echo "---"
  configure_secure_ssh
  echo "---"
  install_docker
  echo "---"
  docker_pull
  echo "---"
  configure_firewall
  echo "---"
  copy_units
  echo "---"
  enable_base_units
  echo "---"
  copy_env_config_files
  echo "---"
  copy_ssl_certs
}

function swarm_init() {
  echo "Initiate swarm..."
  ssh -t "${SSH_USER}@${1}" bash -c "'
    docker swarm init --advertise-addr ${1}
  '"
  echo "done!"
}

function swarm_leave() {
  echo "Purging swarm..."
  ssh -t "${SSH_USER}@${1}" bash -c "'
    docker swarm leave -f
    docker network prune -f
  '"
  echo "done!"
}

function deploy_app() {
  echo "Deploying app to ${SERVER_IP}..."
  scp docker-compose.prod.yml ${SSH_USER}@${1}: &&
  rsync -az nginx ${SSH_USER}@${1}:~
  rsync -az html ${SSH_USER}@${1}:~
  ssh -t "${SSH_USER}@${1}" bash -c "'
    docker stack deploy -c docker-compose.prod.yml haw_app
  '"
  echo "done!"
}

function purge_app() {
  echo "Stoping application on ${SERVER_IP}..."
  ssh -t "${SSH_USER}@${1}" bash -c "'
    docker stack rm haw_app
    rm -rf ~/*
  '"
  echo "done!"
}

function docker_up() {
  echo "Start application locally..."
  docker network create --subnet 172.18.0.0/16 haw_app
  docker-compose up
  echo "done!"
}

function docker_down() {
  echo "Stop application locally..."
  docker-compose down
  docker network prune -f
  docker volume prune -f
  sudo rm -rf node-express/node_modules/ node-express/package-lock.json node-express/public/
  echo "done!"
}
function manual_up() {
  echo "Runing docker manually..."
  echo "Browser acesss from: https://172.18.0.1/"
  docker network create --subnet 172.18.0.0/16 haw_app;
  docker run -d -p 80:80 -p 443:443 \
      -v $PWD/nginx/configs/nginx.conf:/etc/nginx/nginx.conf \
      -v $PWD/nginx/configs/conf.d:/etc/nginx/conf.d \
      -v $PWD/nginx/ssl:/etc/ssl \
      -v $PWD/nginx/frontend:/usr/share/nginx/html \
      --name nginx nginx:1.13
}

function manual_down() {
  echo "Stop docker manually..."
  docker container stop nginx &&
  docker container prune -f ;
  docker network prune -f
}

function help_menu() {
  cat << EOF
  Usage: ${0} [OPTION] <SERVER_IP>

  ENVIRONMENT VARIABLES:
    SERVER_IP         Static IP address to set on the VM Debian server,
                      Defaulting to ${1}

    NETWORK           NEtwork to set on the VM Debian server,
                      Defaulting to ${NETWORK}

    BROADCAST         Broadcast range IP address on the VM Debian server
                      Defaulting to ${BROADCAST}

    LOGIN_PORT        Port to ssh through
                      Defaulting to ${LOGIN_PORT}

    SSH_USER          User account to ssh and scp in as
                      Defaulting to ${SSH_USER}

    KEY_USER          User account linked to the SSH key
                      Defaulting to ${KEY_USER}

  OPTIONS:
    -h|--help                 Show this message
    -S|--preseed-staging      Presseed instructions for the staging server
    -u|--sudo                 Configure passwordless sudo
    -k|--ssh-key              Add SSH key
    -s|--ssh                  Configure secure SSH
    -d|--docker               Install Docker
    -f|--firewall             Configure the iptables firewall
    -c|--copy-units           Copy systemd unit files
    -b|--enable-base-units    Enable base systemd unit files
    -e|--copy-environment     Copy app environment/config files
    -l|--ssl-certs            Copy SSL certificates
    -a|--all                  Provision everything except preseeding
    -i|--swarm-init           Initialize a swarm and networks on ${SERVER_IP}
    -x|--swarm-leave          Leave/delete the swarm and networks on ${SERVER_IP}
    -w|--deploy-app           Deploy application to ${SERVER_IP}
    -t|--stop-app             Stop application on ${SERVER_IP}
    -U|--compose-up           Start application locally
    -D|--compose-down         Stop application locally
    -M|--manually-up          Start application manually
    -X|--manually-down        Stop application started manually

  EXAMPLES:
    Configure passwordless sudo:
          $ ${0} -u

    Configure passwordless sudo on a custom server with ip 192.168.56.10:
          $ ${0} -u 192.168.56.10

    Add SSH key:
          $ ${0} -k

    Add SSH key on a custom server with ip 192.168.56.10:
          $ ${0} -k 192.168.56.10

    Configure secure SSH:
          $ ${0} -s

    Configure secure SSH on a custom server with ip 192.168.56.10:
          $ ${0} -s 192.168.56.10

    Install Docker:
          $ ${0} -d

    Configure the iptables firewall:
          $ ${0} -f

    Copy systemd unit files:
          $ ${0} -c

    Enable base systemd unit files:
          $ ${0} -b

    Copy app environment/config files:
          $ ${0} -e

    Copy SSL certificates:
          $ ${0} -l

    Configure everything together:
          $ ${0} -a

    Start application manually:
          $ ${0} -M
EOF
}

while [[ $# > 0 ]]
do
case "${1}" in
  -S|--preseed-staging)
  preseed_staging ${2:-${SERVER_IP}}
  shift
  ;;
  -u|--sudo)
  configure_sudo ${2:-${SERVER_IP}}
  shift
  ;;
  -k|--shh-key)
  add_ssh_key ${2:-${SERVER_IP}}
  shift
  ;;
  -s|--ssh)
  configure_secure_ssh ${2:-${SERVER_IP}}
  shift
  ;;
  -d|--docker)
  install_docker ${2:-${SERVER_IP}}
  shift
  ;;
  -f|--firewall)
  configure_firewall ${2:-${SERVER_IP}}
  shift
  ;;
  -c|--copy-units)
  copy_units ${2:-${SERVER_IP}}
  shift
  ;;
  -b|--enable-base-units)
  enable_base_units ${2:-${SERVER_IP}}
  shift
  ;;
  -e|--copy-environment)
  copy_env_config_files ${2:-${SERVER_IP}}
  shift
  ;;
  -l|--ssl-certs)
  copy_ssl_certs ${2:-${SERVER_IP}}
  shift
  ;;
  -a|--all)
  provision_server ${2:-${SERVER_IP}}
  shift
  ;;
  -i|--swarm-init)
  swarm_init ${2:-${SERVER_IP}}
  shift
  ;;
  -x|--swarm-leave)
  swarm_leave ${2:-${SERVER_IP}}
  shift
  ;;
  -w|--deploy-app)
  deploy_app ${2:-${SERVER_IP}}
  shift
  ;;
  -t|--stop-app)
  purge_app ${2:-${SERVER_IP}}
  shift
  ;;
  -U|--compose-up)
  docker_up ${2:-${SERVER_IP}}
  shift
  ;;
  -D|--compose-down)
  docker_down ${2:-${SERVER_IP}}
  shift
  ;;
  -M|--manually-up)
  manual_up ${2:-${SERVER_IP}}
  shift
  ;;
  -X|--manually-down)
  manual_down ${2:-${SERVER_IP}}
  shift
  ;;
  -h|--help)
  help_menu ${2:-${SERVER_IP}}
  shift
  ;;
  *)
  echo "${1} is not a valid flag, try running: ${0} --help"
  ;;
esac
shift
done
