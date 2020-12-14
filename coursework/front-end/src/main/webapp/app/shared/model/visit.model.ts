import { Moment } from 'moment';
import { IPet } from 'app/shared/model/pet.model';
import { IVet } from 'app/shared/model/vet.model';
import { VisitStatus } from 'app/shared/model/enumerations/visit-status.model';

export interface IVisit {
  id?: number;
  startDate?: string;
  endDate?: string;
  description?: any;
  status?: VisitStatus;
  pet?: IPet;
  vet?: IVet;
}

export const defaultValue: Readonly<IVisit> = {};
