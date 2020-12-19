import { Moment } from 'moment';

export interface IPetType {
  id?: number;
  type?: string;
  test?: string;
}

export const defaultValue: Readonly<IPetType> = {};
