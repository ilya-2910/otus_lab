import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPetType, defaultValue } from 'app/shared/model/pet-type.model';

export const ACTION_TYPES = {
  SEARCH_PETTYPES: 'petType/SEARCH_PETTYPES',
  FETCH_PETTYPE_LIST: 'petType/FETCH_PETTYPE_LIST',
  FETCH_PETTYPE: 'petType/FETCH_PETTYPE',
  CREATE_PETTYPE: 'petType/CREATE_PETTYPE',
  UPDATE_PETTYPE: 'petType/UPDATE_PETTYPE',
  DELETE_PETTYPE: 'petType/DELETE_PETTYPE',
  RESET: 'petType/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPetType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PetTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: PetTypeState = initialState, action): PetTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PETTYPES):
    case REQUEST(ACTION_TYPES.FETCH_PETTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PETTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PETTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_PETTYPE):
    case REQUEST(ACTION_TYPES.DELETE_PETTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_PETTYPES):
    case FAILURE(ACTION_TYPES.FETCH_PETTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PETTYPE):
    case FAILURE(ACTION_TYPES.CREATE_PETTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_PETTYPE):
    case FAILURE(ACTION_TYPES.DELETE_PETTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PETTYPES):
    case SUCCESS(ACTION_TYPES.FETCH_PETTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PETTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PETTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_PETTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PETTYPE):
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

const apiUrl = 'api/pet-types';
const apiSearchUrl = 'api/_search/pet-types';

// Actions

export const getSearchEntities: ICrudSearchAction<IPetType> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PETTYPES,
  payload: axios.get<IPetType>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IPetType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PETTYPE_LIST,
  payload: axios.get<IPetType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPetType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PETTYPE,
    payload: axios.get<IPetType>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPetType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PETTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPetType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PETTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPetType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PETTYPE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
