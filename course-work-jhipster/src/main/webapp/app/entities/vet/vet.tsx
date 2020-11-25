import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './vet.reducer';
import { IVet } from 'app/shared/model/vet.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVetProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Vet = (props: IVetProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { vetList, match, loading } = props;
  return (
    <div>
      <h2 id="vet-heading">
        <Translate contentKey="courseWorkApp.vet.home.title">Vets</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="courseWorkApp.vet.home.createLabel">Create new Vet</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {vetList && vetList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="courseWorkApp.vet.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="courseWorkApp.vet.phone">Phone</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vetList.map((vet, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${vet.id}`} color="link" size="sm">
                      {vet.id}
                    </Button>
                  </td>
                  <td>{vet.name}</td>
                  <td>{vet.phone}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${vet.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vet.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vet.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="courseWorkApp.vet.home.notFound">No Vets found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ vet }: IRootState) => ({
  vetList: vet.entities,
  loading: vet.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Vet);
