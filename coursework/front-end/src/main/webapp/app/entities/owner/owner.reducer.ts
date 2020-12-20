import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOwner, defaultValue } from 'app/shared/model/owner.model';

export const ACTION_TYPES = {
  FETCH_OWNER_LIST: 'owner/FETCH_OWNER_LIST',
  FETCH_OWNER: 'owner/FETCH_OWNER',
  CREATE_OWNER: 'owner/CREATE_OWNER',
  UPDATE_OWNER: 'owner/UPDATE_OWNER',
  DELETE_OWNER: 'owner/DELETE_OWNER',
  RESET: 'owner/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOwner>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type OwnerState = Readonly<typeof initialState>;

// Reducer

export default (state: OwnerState = initialState, action): OwnerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OWNER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OWNER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_OWNER):
    case REQUEST(ACTION_TYPES.UPDATE_OWNER):
    case REQUEST(ACTION_TYPES.DELETE_OWNER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_OWNER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OWNER):
    case FAILURE(ACTION_TYPES.CREATE_OWNER):
    case FAILURE(ACTION_TYPES.UPDATE_OWNER):
    case FAILURE(ACTION_TYPES.DELETE_OWNER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OWNER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OWNER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_OWNER):
    case SUCCESS(ACTION_TYPES.UPDATE_OWNER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_OWNER):
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

const apiUrl = 'api/owners';

// Actions

export const getEntities: ICrudGetAllAction<IOwner> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_OWNER_LIST,
  payload: axios.get<IOwner>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IOwner> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OWNER,
    payload: axios.get<IOwner>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IOwner> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OWNER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOwner> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OWNER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOwner> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OWNER,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
