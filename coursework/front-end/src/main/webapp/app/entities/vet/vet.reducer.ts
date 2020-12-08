import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVet, defaultValue } from 'app/shared/model/vet.model';

export const ACTION_TYPES = {
  SEARCH_VETS: 'vet/SEARCH_VETS',
  FETCH_VET_LIST: 'vet/FETCH_VET_LIST',
  FETCH_VET: 'vet/FETCH_VET',
  CREATE_VET: 'vet/CREATE_VET',
  UPDATE_VET: 'vet/UPDATE_VET',
  DELETE_VET: 'vet/DELETE_VET',
  RESET: 'vet/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVet>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type VetState = Readonly<typeof initialState>;

// Reducer

export default (state: VetState = initialState, action): VetState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_VETS):
    case REQUEST(ACTION_TYPES.FETCH_VET_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_VET):
    case REQUEST(ACTION_TYPES.UPDATE_VET):
    case REQUEST(ACTION_TYPES.DELETE_VET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_VETS):
    case FAILURE(ACTION_TYPES.FETCH_VET_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VET):
    case FAILURE(ACTION_TYPES.CREATE_VET):
    case FAILURE(ACTION_TYPES.UPDATE_VET):
    case FAILURE(ACTION_TYPES.DELETE_VET):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_VETS):
    case SUCCESS(ACTION_TYPES.FETCH_VET_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_VET):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_VET):
    case SUCCESS(ACTION_TYPES.UPDATE_VET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_VET):
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

const apiUrl = 'api/vets';
const apiSearchUrl = 'api/_search/vets';

// Actions

export const getSearchEntities: ICrudSearchAction<IVet> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_VETS,
  payload: axios.get<IVet>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IVet> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VET_LIST,
  payload: axios.get<IVet>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IVet> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VET,
    payload: axios.get<IVet>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IVet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VET,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VET,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVet> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VET,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
