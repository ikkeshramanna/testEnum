import { IPersonAddresses } from 'app/shared/model/person-addresses.model';
import { IPersonDetails } from 'app/shared/model/person-details.model';

export interface IPerson {
  id?: number;
  name?: string;
  age?: number;
  addresses?: IPersonAddresses[];
  details?: IPersonDetails[];
}

export class Person implements IPerson {
  constructor(
    public id?: number,
    public name?: string,
    public age?: number,
    public addresses?: IPersonAddresses[],
    public details?: IPersonDetails[]
  ) {}
}
