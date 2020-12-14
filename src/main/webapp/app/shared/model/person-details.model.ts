import { IPerson } from 'app/shared/model/person.model';

export interface IPersonDetails {
  id?: number;
  nid?: string;
  dob?: string;
  person?: IPerson;
}

export class PersonDetails implements IPersonDetails {
  constructor(public id?: number, public nid?: string, public dob?: string, public person?: IPerson) {}
}
