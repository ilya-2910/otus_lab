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

export const Visit = (props: IVisitProps) => {

  function onTimeChange(visibleTimeStart, visibleTimeEnd, updateScrollCanvas) {
    // this limits the timeline to -6 months ... +6 months
    const minTime = moment().add(-26, 'hours').valueOf();
    const maxTime = moment().add(26, 'hours').valueOf();

    if (visibleTimeStart < minTime && visibleTimeEnd > maxTime) {
      updateScrollCanvas(minTime, maxTime)
    } else if (visibleTimeStart < minTime) {
      updateScrollCanvas(minTime, minTime + (visibleTimeEnd - visibleTimeStart))
    } else if (visibleTimeEnd > maxTime) {
      updateScrollCanvas(maxTime - (visibleTimeEnd - visibleTimeStart), maxTime)
    } else {
      updateScrollCanvas(visibleTimeStart, visibleTimeEnd)
    }
  }


  useEffect(() => {
    props.getEntities();
  }, []);

  const { visitList, match, loading } = props;

  let vetArr = visitList.map(value => value.vet);

  let groups2 = vetArr.map((value, index) => {
    return {id: value.id, title: value.name};
  }).filter((value, index, self) => self.map(x => x.id).indexOf(value.id) === index);

  const items2 = visitList.map((value, index) => {
    return {
      id: index,
      group: value.vet.id,
      title: value.pet.name,
      start_time: moment(value.startDate),
      end_time: moment(value.endDate),
      canResize: false,
      canMove: false,
      maxZoom: 60 * 60 * 1000,
      style: {
        background: 'fuchsia'
      }

    }
  });

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
        itemsSorted
        itemTouchSendsClick={false}
        canMove={false}
        canResize={false}
        minZoom={60 * 1000 * 1000}
        groups={groups2}
        items={items2}
        defaultTimeStart={moment().add(-12, 'hour')}
        defaultTimeEnd={moment().add(12, 'hour')}
        sidebarWidth={250}
        onTimeChange={onTimeChange}
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
                  <td>{visit.pet ? <Link to={`pet/${visit.pet.id}`}>{visit.pet.name}</Link> : ''}</td>
                  <td>{visit.vet ? <Link to={`vet/${visit.vet.id}`}>{visit.vet.name}</Link> : ''}</td>
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
