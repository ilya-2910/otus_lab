import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './visit.reducer';
import { IVisit } from 'app/shared/model/visit.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import moment from "moment";
import Timeline from 'react-calendar-timeline'
import 'react-calendar-timeline/lib/Timeline.css'

export interface IVisitProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

const groups = [{ id: 1, title: 'Иванов И В' }, { id: 2, title: 'Петров В В' }];

const items = [
  {
    id: 1,
    group: 1,
    title: 'Иванов в',
    start_time: moment(),
    end_time: moment().add(1, 'hour')
  },
  {
    id: 2,
    group: 2,
    title: 'item 2',
    start_time: moment().add(-0.5, 'hour'),
    end_time: moment().add(0.5, 'hour')
  },
  {
    id: 3,
    group: 1,
    title: 'item 3',
    start_time: moment().add(2, 'hour'),
    end_time: moment().add(3, 'hour')
  }
]


export const Visit = (props: IVisitProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { visitList, match, loading } = props;
  return (
    <div>
      <h2 id="visit-heading">
        <Translate contentKey="courseWorkApp.visit.home.title">Visits</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="courseWorkApp.visit.home.createLabel">Create new Visit</Translate>
        </Link>
      </h2>
      <Timeline
        groups={groups}
        items={items}
        defaultTimeStart={moment().add(-12, 'hour')}
        defaultTimeEnd={moment().add(12, 'hour')}
      />
      <p/>
      <div className="table-responsive">
        {visitList && visitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="courseWorkApp.visit.startDate">Start Date</Translate>
                </th>
                <th>
                  <Translate contentKey="courseWorkApp.visit.endDate">End Date</Translate>
                </th>
                <th>
                  <Translate contentKey="courseWorkApp.visit.pet">Pet</Translate>
                </th>
                <th>
                  <Translate contentKey="courseWorkApp.visit.vet">Vet</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {visitList.map((visit, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${visit.id}`} color="link" size="sm">
                      {visit.id}
                    </Button>
                  </td>
                  <td>{visit.startDate ? <TextFormat type="date" value={visit.startDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{visit.endDate ? <TextFormat type="date" value={visit.endDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{visit.pet ? <Link to={`pet/${visit.pet.id}`}>{visit.pet.id}</Link> : ''}</td>
                  <td>{visit.vet ? <Link to={`vet/${visit.vet.id}`}>{visit.vet.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${visit.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${visit.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${visit.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="courseWorkApp.visit.home.notFound">No Visits found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ visit }: IRootState) => ({
  visitList: visit.entities,
  loading: visit.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Visit);
