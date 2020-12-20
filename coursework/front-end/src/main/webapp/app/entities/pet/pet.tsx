import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './pet.reducer';
import { IPet } from 'app/shared/model/pet.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPetProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Pet = (props: IPetProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { petList, match, loading } = props;
  return (
    <div>
      <h2 id="pet-heading">
        <Translate contentKey="courseworkApp.pet.home.title">Pets</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="courseworkApp.pet.home.createLabel">Create new Pet</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {petList && petList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.pet.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.pet.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.pet.owner">Owner</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {petList.map((pet, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${pet.id}`} color="link" size="sm">
                      {pet.id}
                    </Button>
                  </td>
                  <td>{pet.name}</td>
                  <td>{pet.type ? <Link to={`pet-type/${pet.type.id}`}>{pet.type.id}</Link> : ''}</td>
                  <td>{pet.owner ? <Link to={`owner/${pet.owner.id}`}>{pet.owner.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${pet.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${pet.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${pet.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="courseworkApp.pet.home.notFound">No Pets found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ pet }: IRootState) => ({
  petList: pet.entities,
  loading: pet.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Pet);
