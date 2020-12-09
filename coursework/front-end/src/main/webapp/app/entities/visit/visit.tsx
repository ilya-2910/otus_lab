import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './visit.reducer';
import { IVisit } from 'app/shared/model/visit.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import moment from "moment";
import Timeline from 'react-calendar-timeline'
import 'react-calendar-timeline/lib/Timeline.css'

export interface IVisitProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Visit = (props: IVisitProps) => {
  const [search, setSearch] = useState('');

  useEffect(() => {
    props.getEntities();
  }, []);

  const startSearching = () => {
    if (search) {
      props.getSearchEntities(search);
    }
  };

  const clear = () => {
    setSearch('');
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

  const { visitList, match, loading } = props;

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

  const visits = visitList.filter(value => !!value.vet && !!value.pet);
  const vets = visits.map(value => value.vet);

  const groups = vets.map((value, index) => {
    return {id: value.id, title: value.name};
  }).filter((value, index, self) => self.map(x => x.id).indexOf(value.id) === index);

  const items = visits.map((value, index) => {
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
        Visits
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Visit
        </Link>
      </h2>
      <Timeline
        itemsSorted
        itemTouchSendsClick={false}
        canMove={false}
        canResize={false}
        minZoom={60 * 1000 * 1000}
        groups={groups}
        items={items}
        defaultTimeStart={moment().add(-12, 'hour')}
        defaultTimeEnd={moment().add(12, 'hour')}
        sidebarWidth={250}
        onTimeChange={onTimeChange}
      />
      <Row>
        <Col sm="12">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput type="text" name="search" value={search} onChange={handleSearch} placeholder="Search" />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </AvGroup>
          </AvForm>
        </Col>
      </Row>
      <div className="table-responsive">
        {visitList && visitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Pet</th>
                <th>Vet</th>
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
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${visit.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${visit.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Visits found</div>
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
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Visit);
