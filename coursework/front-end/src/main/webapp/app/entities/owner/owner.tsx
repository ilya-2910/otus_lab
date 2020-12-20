import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './owner.reducer';
import { IOwner } from 'app/shared/model/owner.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOwnerProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Owner = (props: IOwnerProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { ownerList, match, loading } = props;
  return (
    <div>
      <h2 id="owner-heading">
        <Translate contentKey="courseworkApp.owner.home.title">Owners</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="courseworkApp.owner.home.createLabel">Create new Owner</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {ownerList && ownerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.owner.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.owner.address">Address</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.owner.phone">Phone</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ownerList.map((owner, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${owner.id}`} color="link" size="sm">
                      {owner.id}
                    </Button>
                  </td>
                  <td>{owner.name}</td>
                  <td>{owner.address}</td>
                  <td>{owner.phone}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${owner.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${owner.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${owner.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="courseworkApp.owner.home.notFound">No Owners found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ owner }: IRootState) => ({
  ownerList: owner.entities,
  loading: owner.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Owner);
