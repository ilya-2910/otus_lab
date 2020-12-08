import { Moment } from 'moment';
import { IPet } from 'app/shared/model/pet.model';
import { IVet } from 'app/shared/model/vet.model';

export interface IVisit {
  id?: number;
  startDate?: string;
  endDate?: string;
  pet?: IPet;
  vet?: IVet;
}

export const defaultValue: Readonly<IVisit> = {};
