export class Statistics {
  group: string;
  user: string;
  jobid: string;
  nodes: string;
  procs: string;
  stime: any;
  etime: any;
  subtime: any;
  utime: any;

  constructor(
      group: string,
      user: string,
      jobid: string,
      nodes: string,
      procs: string,
      stime: any,
      etime: any,
      subtime: any,
      utime: any) {
    this.group = group;
    this.user = user;
    this.jobid = jobid;
    this.nodes = nodes;
    this.procs = procs;
    this.stime = stime;
    this.etime = etime;
    this.subtime = subtime;
    const hours: number = (utime / 3600) >> 0;
    const minutes: number = ((utime - hours * 3600)) / 60 >> 0;
    const seconds: number = utime % 60;
    this.utime = doubleDigits(hours) + ':' + doubleDigits(minutes)
      + ':' + doubleDigits(seconds);

    function doubleDigits(n:number) {
      return n < 10 ? "0" + n: n;
    }
  };
}
