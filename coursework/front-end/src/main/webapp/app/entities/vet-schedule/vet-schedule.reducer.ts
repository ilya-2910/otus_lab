import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVetSchedule, defaultValue } from 'app/shared/model/vet-schedule.model';

export const ACTION_TYPES = {
  FETCH_VETSCHEDULE_LIST: 'vetSchedule/FETCH_VETSCHEDULE_LIST',
  FETCH_VETSCHEDULE: 'vetSchedule/FETCH_VETSCHEDULE',
  CREATE_VETSCHEDULE: 'vetSchedule/CREATE_VETSCHEDULE',
  UPDATE_VETSCHEDULE: 'vetSchedule/UPDATE_VETSCHEDULE',
  DELETE_VETSCHEDULE: 'vetSchedule/DELETE_VETSCHEDULE',
  RESET: 'vetSchedule/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVetSchedule>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type VetScheduleState = Readonly<typeof initialState>;

// Reducer

export default (state: VetScheduleState = initialState, action): VetScheduleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VETSCHEDULE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VETSCHEDULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_VETSCHEDULE):
    case REQUEST(ACTION_TYPES.UPDATE_VETSCHEDULE):
    case REQUEST(ACTION_TYPES.DELETE_VETSCHEDULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_VETSCHEDULE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VETSCHEDULE):
    case FAILURE(ACTION_TYPES.CREATE_VETSCHEDULE):
    case FAILURE(ACTION_TYPES.UPDATE_VETSCHEDULE):
    case FAILURE(ACTION_TYPES.DELETE_VETSCHEDULE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_VETSCHEDULE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_VETSCHEDULE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_VETSCHEDULE):
    case SUCCESS(ACTION_TYPES.UPDATE_VETSCHEDULE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_VETSCHEDULE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/vet-schedules';

// Actions

export const getEntities: ICrudGetAllAction<IVetSchedule> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VETSCHEDULE_LIST,
  payload: axios.get<IVetSchedule>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IVetSchedule> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VETSCHEDULE,
    payload: axios.get<IVetSchedule>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IVetSchedule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VETSCHEDULE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVetSchedule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VETSCHEDULE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVetSchedule> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VETSCHEDULE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
