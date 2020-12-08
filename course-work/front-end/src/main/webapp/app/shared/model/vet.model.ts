import { IVisit } from 'app/shared/model/visit.model';

export interface IVet {
  id?: number;
  name?: string;
  phone?: string;
  visits?: IVisit[];
}

export const defaultValue: Readonly<IVet> = {};
