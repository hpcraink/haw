export class User {
  constructor(
    public email: string,
    public password: string,
    public firstName?: string,
    public lastName?: string,
    public country?: string,
    public address?: string,
    public town?: string,
    public postcode?: string,
    public gender?: string,
    public uni?: string
  ){}
}
