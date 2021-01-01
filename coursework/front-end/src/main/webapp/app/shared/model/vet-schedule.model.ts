import { Moment } from 'moment';
import { IVet } from 'app/shared/model/vet.model';

export interface IVetSchedule {
  id?: number;
  startDate?: string;
  endDate?: string;
  vet?: IVet;
}

export const defaultValue: Readonly<IVetSchedule> = {};
