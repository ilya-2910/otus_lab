import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVisit, defaultValue } from 'app/shared/model/visit.model';

export const ACTION_TYPES = {
  SEARCH_VISITS: 'visit/SEARCH_VISITS',
  FETCH_VISIT_LIST: 'visit/FETCH_VISIT_LIST',
  FETCH_VISIT: 'visit/FETCH_VISIT',
  CREATE_VISIT: 'visit/CREATE_VISIT',
  UPDATE_VISIT: 'visit/UPDATE_VISIT',
  DELETE_VISIT: 'visit/DELETE_VISIT',
  RESET: 'visit/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVisit>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type VisitState = Readonly<typeof initialState>;

// Reducer

export default (state: VisitState = initialState, action): VisitState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_VISITS):
    case REQUEST(ACTION_TYPES.FETCH_VISIT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VISIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_VISIT):
    case REQUEST(ACTION_TYPES.UPDATE_VISIT):
    case REQUEST(ACTION_TYPES.DELETE_VISIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_VISITS):
    case FAILURE(ACTION_TYPES.FETCH_VISIT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VISIT):
    case FAILURE(ACTION_TYPES.CREATE_VISIT):
    case FAILURE(ACTION_TYPES.UPDATE_VISIT):
    case FAILURE(ACTION_TYPES.DELETE_VISIT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_VISITS):
    case SUCCESS(ACTION_TYPES.FETCH_VISIT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_VISIT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_VISIT):
    case SUCCESS(ACTION_TYPES.UPDATE_VISIT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_VISIT):
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

const apiUrl = 'api/visits';
const apiSearchUrl = 'api/_search/visits';

// Actions

export const getSearchEntities: ICrudSearchAction<IVisit> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_VISITS,
  payload: axios.get<IVisit>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IVisit> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VISIT_LIST,
  payload: axios.get<IVisit>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IVisit> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VISIT,
    payload: axios.get<IVisit>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IVisit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VISIT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVisit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VISIT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVisit> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VISIT,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
