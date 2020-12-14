import { IVisit } from 'app/shared/model/visit.model';
import { IPetType } from 'app/shared/model/pet-type.model';
import { IOwner } from 'app/shared/model/owner.model';

export interface IPet {
  id?: number;
  name?: string;
  visits?: IVisit[];
  type?: IPetType;
  owner?: IOwner;
}

export const defaultValue: Readonly<IPet> = {};
