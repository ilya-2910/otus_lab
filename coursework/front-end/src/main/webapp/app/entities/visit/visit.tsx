import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table, InputGroup, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { byteSize, Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import {getEntities, getEntitiesFilter} from './visit.reducer';
import { getEntitiesFilter as getVetScheduleEntitiesFilter} from '../vet-schedule/vet-schedule.reducer';
import { IVisit } from 'app/shared/model/visit.model';
import { APP_DATE_FORMAT} from 'app/config/constants';

import moment from "moment";
import Timeline from 'react-calendar-timeline'
import 'react-calendar-timeline/lib/Timeline.css'


export interface IVisitProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Visit = (props: IVisitProps) => {

  const [date, setDate] = useState(moment().startOf('day'));

  const searchEntities = () => {
    props.getEntitiesFilter(
      {'startDate.lessThan': moment(date.clone().add(1, 'day').clone()).toDate(),
      'endDate.greaterThan': date.clone().add(0, 'day').toDate()});
  }

  const searchVetSchedulesEntities = () => {
    props.getVetScheduleEntitiesFilter(
      {'startDate.lessThan': moment(date.clone().add(1, 'day').clone()).toDate(),
        'endDate.greaterThan': date.clone().add(0, 'day').toDate()});
  }

  const itemRenderer = ({
                   item,
                   itemContext,
                   getItemProps,
                   getResizeProps
                 }) => {
    const { left: leftResizeProps, right: rightResizeProps } = getResizeProps()
    return (
      <div {...getItemProps({style: {backgroundColor: item.color}})}>
        {itemContext.useResizeHandle ? <div {...leftResizeProps} /> : ''}

        <div
          className="rct-item-content"
          style={{ maxHeight: `${itemContext.dimensions.height}` }}
        >
          {itemContext.title}
        </div>

        {itemContext.useResizeHandle ? <div {...rightResizeProps} /> : ''}
      </div>
    )}

  // const itemRenderer = ({ item, timelineContext, itemContext, getItemProps, getResizeProps }) => {
  //   const { left: leftResizeProps, right: rightResizeProps } = getResizeProps();
  //   const backgroundColor = itemContext.selected ? (itemContext.dragging ? "red" : item.selectedBgColor) : item.bgColor;
  //   const borderColor = itemContext.resizing ? "red" : item.color;
  //   const props1 = getItemProps({
  //     style: {
  //       backgroundColor,
  //       color: item.color,
  //       borderColor,
  //       borderStyle: "solid",
  //       borderWidth: 1,
  //       borderRadius: 4,
  //       borderLeftWidth: itemContext.selected ? 3 : 1,
  //       borderRightWidth: itemContext.selected ? 3 : 1
  //     },
  //   });
  //   props1.style.height = Math.random() * 30;
  //   return (
  //     <div {...props1}>
  //       {itemContext.useResizeHandle ? <div {...leftResizeProps} /> : null}
  //
  //       <div
  //         style={{
  //           height: itemContext.dimensions.height,
  //           overflow: "hidden",
  //           paddingLeft: 3,
  //           textOverflow: "ellipsis",
  //           whiteSpace: "nowrap"
  //         }}
  //       >
  //         {itemContext.title}
  //       </div>
  //
  //       {itemContext.useResizeHandle ? <div {...rightResizeProps} /> : null}
  //     </div>
  //   );
  // };

  useEffect(() => {
    searchVetSchedulesEntities();
    searchEntities();
  }, [date]);

  const changeDate = (dayDiff: number) => {
    if (date) {
      setDate(moment(date).add(dayDiff, 'days'));
    }
  };

  const { visitList, vetScheduleList, match, loading } = props;


  const visits = visitList.filter(value => !!value.vet && !!value.pet);

  const vetScheduleGroup = vetScheduleList.map((value, index) => {
    return {id: value.vet.id, title: value.vet.name};
  }).filter((value, index, self) => self.map(x => x.id).indexOf(value.id) === index);

  let vetScheduleItems = vetScheduleList.map((value, index) => {
    return {
      id: index,
      group: value.vet.id,
      title: '',
      color: 'rgba(0, 0, 0, 0.05)',
      // eslint-disable-next-line @typescript-eslint/camelcase
      start_time: moment(value.startDate),
      // eslint-disable-next-line @typescript-eslint/camelcase
      end_time: moment(value.endDate),
      canResize: true,
      canMove: true,
      maxZoom: 60 * 60 * 1000,
      style: {
        background: 'indigo'
      }
    }
  })

  const visitItems = visits.map((value, index) => {
    return {
      id: index + 1000,
      group: value.vet.id,
      title: value.pet.name,
      color : 'rgba(244,0,40,0.6)',
      // eslint-disable-next-line @typescript-eslint/camelcase
      start_time: moment(value.startDate),
      // eslint-disable-next-line @typescript-eslint/camelcase
      end_time: moment(value.endDate),
      canResize: true,
      canMove: true,
      maxZoom: 60 * 60 * 1000,
      style: {
        background: 'indigo'
      }
    }
  });
  vetScheduleItems = vetScheduleItems.concat(visitItems);

  return (
    <div>
      <h2 id="visit-heading">
        <Translate contentKey="courseworkApp.visit.home.title">Visits</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="courseworkApp.visit.home.createLabel">Create new Visit</Translate>
        </Link>
      </h2>
      {!loading ? (
        <Timeline
          itemRenderer={itemRenderer}
          itemsSorted
          itemTouchSendsClick={false}
          canMove={false}
          canResize={false}
          minZoom={60 * 1000 * 1000}
          groups={vetScheduleGroup}
          items={vetScheduleItems}
          visibleTimeStart={moment(date).startOf('day')}
          visibleTimeEnd={moment(date).add(1, 'day').startOf('day')}
          sidebarWidth={250}
        />
      ) : null}
      <Row>
        <Col sm="12">
          <div style={{width: 300}}>
          {/*<AvForm onSubmit={startSearching}>*/}
          <AvForm>
            <AvGroup>
              <Label id="startDateLabel" for="visit-date">
                Дата
              </Label>
              <InputGroup>
              <AvInput
                id="visit-date"
                type="date"
                className="form-control"
                name="startDate"
                value={date.format().slice(0, 10)}
//                 onChange={ (e) => {
//                   setDate(moment(e.target.value));
// //                  startSearching();
//                 }}
                />
              <Button color={'btn btn-primary'} onClick={() => changeDate(-1)}>
                &lt;
              </Button>
              <Button color={'btn btn-primary'} onClick={() => changeDate( +1)}>
                &gt;
              </Button>
                </InputGroup>
            </AvGroup>
            {/*<AvGroup>*/}
            {/*  <InputGroup>*/}
            {/*    <AvInput type="text" name="searchEntities" value={searchEntities} onChange={handleSearch} placeholder="Search" />*/}
            {/*    <Button className="input-group-addon">*/}
            {/*      <FontAwesomeIcon icon="searchEntities" />*/}
            {/*    </Button>*/}
            {/*    <Button type="reset" className="input-group-addon" onClick={clear}>*/}
            {/*      <FontAwesomeIcon icon="trash" />*/}
            {/*    </Button>*/}
            {/*  </InputGroup>*/}
            {/*</AvGroup>*/}
          </AvForm>
            </div>
        </Col>
      </Row>
      <div className="table-responsive">
        {visitList && visitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.visit.startDate">Start Date</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.visit.endDate">End Date</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.visit.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.visit.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.visit.pet">Pet</Translate>
                </th>
                <th>
                  <Translate contentKey="courseworkApp.visit.vet">Vet</Translate>
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
                  <td>{visit.description}</td>
                  <td>
                    <Translate contentKey={`courseworkApp.VisitStatus.${visit.status}`} />
                  </td>
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
              <Translate contentKey="courseworkApp.visit.home.notFound">No Visits found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ visit , vetSchedule}: IRootState) => ({
  visitList: visit.entities,
  vetScheduleList: vetSchedule.entities,
  loading: visit.loading,
});

const mapDispatchToProps = {
  getEntities, getEntitiesFilter, getVetScheduleEntitiesFilter
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Visit);
