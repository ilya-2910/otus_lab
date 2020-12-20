import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPet, defaultValue } from 'app/shared/model/pet.model';

export const ACTION_TYPES = {
  FETCH_PET_LIST: 'pet/FETCH_PET_LIST',
  FETCH_PET: 'pet/FETCH_PET',
  CREATE_PET: 'pet/CREATE_PET',
  UPDATE_PET: 'pet/UPDATE_PET',
  DELETE_PET: 'pet/DELETE_PET',
  RESET: 'pet/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPet>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PetState = Readonly<typeof initialState>;

// Reducer

export default (state: PetState = initialState, action): PetState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PET_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PET):
    case REQUEST(ACTION_TYPES.UPDATE_PET):
    case REQUEST(ACTION_TYPES.DELETE_PET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PET_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PET):
    case FAILURE(ACTION_TYPES.CREATE_PET):
    case FAILURE(ACTION_TYPES.UPDATE_PET):
    case FAILURE(ACTION_TYPES.DELETE_PET):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PET_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PET):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PET):
    case SUCCESS(ACTION_TYPES.UPDATE_PET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PET):
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

const apiUrl = 'api/pets';

// Actions

export const getEntities: ICrudGetAllAction<IPet> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PET_LIST,
  payload: axios.get<IPet>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPet> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PET,
    payload: axios.get<IPet>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PET,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PET,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPet> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PET,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
