import { Moment } from 'moment';
import { VisitStatus } from 'app/shared/model/enumerations/visit-status.model';
import { IPet } from 'app/shared/model/pet.model';
import { IVet } from 'app/shared/model/vet.model';

export interface IVisitExt {
  id?: number;
  startDate?: string;
  endDate?: string;
  description?: any;
  status?: VisitStatus;
  pet?: IPet;
  vet?: IVet;
}

export const defaultValue: Readonly<IVisitExt> = {};
