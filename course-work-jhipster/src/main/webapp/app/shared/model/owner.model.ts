import { IPet } from 'app/shared/model/pet.model';

export interface IOwner {
  id?: number;
  name?: string;
  address?: string;
  phone?: string;
  pets?: IPet[];
}

export const defaultValue: Readonly<IOwner> = {};
