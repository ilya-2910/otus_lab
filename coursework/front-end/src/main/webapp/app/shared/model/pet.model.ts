import { IVisit } from 'app/shared/model/visit.model';
import { IOwner } from 'app/shared/model/owner.model';
import { PetType } from 'app/shared/model/enumerations/pet-type.model';

export interface IPet {
  id?: number;
  name?: string;
  type?: PetType;
  visits?: IVisit[];
  owner?: IOwner;
}

export const defaultValue: Readonly<IPet> = {};
