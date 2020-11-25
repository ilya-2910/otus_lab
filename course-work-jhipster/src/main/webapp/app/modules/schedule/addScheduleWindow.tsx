import React from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter} from 'reactstrap';
import { Button, Form, FormGroup, Label, Input, FormText } from 'reactstrap';
 import DatePicker from "reactstrap-date-picker";
 //var DatePicker = require("reactstrap-date-picker");


const AddScheduleWindow = ({ handleClose, healthObject, showModal }) => {
  const data = healthObject.details || {};
  return (
    <Modal isOpen={showModal} modalTransition={{ timeout: 20 }} backdropTransition={{ timeout: 10 }} toggle={handleClose}>
      <ModalHeader toggle={handleClose}>{healthObject.name}</ModalHeader>
      <ModalBody>
        <Form>
          <FormGroup>
            <Label for="exampleSelect">Выберите врача</Label>
            <Input type="select" name="select" id="exampleSelect">
              <option>Иванов И И</option>
              <option>Петров И И</option>
            </Input>
          </FormGroup>
          <FormGroup>
            <Label for="exampleSelect2">Выберите пациента</Label>
            <Input type="select" name="select" id="exampleSelect2">
              <option>Собака шарик</option>
              <option>Кот барсик</option>
            </Input>
          </FormGroup>
          <FormGroup>
            <Label for="exampleSelect2">Выберите дату</Label>
            <DatePicker/>
          </FormGroup>
          <FormGroup>
            <Label for="exampleSelect2">Выберите время начала приёма</Label>
            <Input type="select" name="select" id="exampleSelect2">
              <option>10:10</option>
              <option>10:20</option>
            </Input>
          </FormGroup>
          <FormGroup>
            <Label for="exampleSelect2">Выберите время окончания приёма</Label>
            <Input type="select" name="select" id="exampleSelect2">
              <option>10:10</option>
              <option>10:30</option>
            </Input>
          </FormGroup>
        </Form>
      </ModalBody>
      <ModalFooter>
        <Button color="primary" onClick={handleClose}>
          Сохранить
        </Button>
        <Button color="primary" onClick={handleClose}>
          Close
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default AddScheduleWindow;
