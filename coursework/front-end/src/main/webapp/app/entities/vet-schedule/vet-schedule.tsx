import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './vet-schedule.reducer';
import { IVetSchedule } from 'app/shared/model/vet-schedule.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVetScheduleProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const VetSchedule = (props: IVetScheduleProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { vetScheduleList, match, loading } = props;
  return (
    <div>
      <h2 id="vet-schedule-heading">
        <Translate contentKey="courseworkApp.vetSchedule.home.title">Vet Schedules</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="courseworkApp.vetSchedule.home.createLabel">Create new Vet Schedule</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {vetScheduleList && vetScheduleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.vetSchedule.startDate">Start Date</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.vetSchedule.endDate">End Date</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.vetSchedule.vet">Vet</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vetScheduleList.map((vetSchedule, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${vetSchedule.id}`} color="link" size="sm">
                      {vetSchedule.id}
                    </Button>
                  </td>
                  <td>
                    {vetSchedule.startDate ? <TextFormat type="date" value={vetSchedule.startDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{vetSchedule.endDate ? <TextFormat type="date" value={vetSchedule.endDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{vetSchedule.vet ? <Link to={`vet/${vetSchedule.vet.id}`}>{vetSchedule.vet.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${vetSchedule.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vetSchedule.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vetSchedule.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="courseworkApp.vetSchedule.home.notFound">No Vet Schedules found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ vetSchedule }: IRootState) => ({
  vetScheduleList: vetSchedule.entities,
  loading: vetSchedule.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VetSchedule);
