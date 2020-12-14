import { IPerson } from 'app/shared/model/person.model';
import { TestType } from 'app/shared/model/enumerations/test-type.model';

export interface IPersonAddresses {
  id?: number;
  address?: string;
  zipcode?: number;
  test?: TestType;
  person?: IPerson;
}

export class PersonAddresses implements IPersonAddresses {
  constructor(public id?: number, public address?: string, public zipcode?: number, public test?: TestType, public person?: IPerson) {}
}
