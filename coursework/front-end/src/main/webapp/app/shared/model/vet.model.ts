import { IVisit } from 'app/shared/model/visit.model';
import { IVetSchedule } from 'app/shared/model/vet-schedule.model';

export interface IVet {
  id?: number;
  name?: string;
  phone?: string;
  visits?: IVisit[];
  schedules?: IVetSchedule[];
}

export const defaultValue: Readonly<IVet> = {};
