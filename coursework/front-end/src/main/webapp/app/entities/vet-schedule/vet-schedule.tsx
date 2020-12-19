import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './vet-schedule.reducer';
import { IVetSchedule } from 'app/shared/model/vet-schedule.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVetScheduleProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const VetSchedule = (props: IVetScheduleProps) => {
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

  const { vetScheduleList, match, loading } = props;
  return (
    <div>
      <h2 id="vet-schedule-heading">
        Vet Schedules
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Vet Schedule
        </Link>
      </h2>
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
        {vetScheduleList && vetScheduleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Vet</th>
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
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vetSchedule.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vetSchedule.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Vet Schedules found</div>
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
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VetSchedule);
