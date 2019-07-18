import React from 'react';
import userData from '../_models/users.model';
import DateTimePicker from 'react-datetime-picker'; 
import DatePicker from 'react-date-picker';
import TimePicker from 'react-time-picker';
import Moment from 'react-moment';
import moment from 'moment';
import './meal.css'

import {mealServices} from '../_services/meals.services'

import Alert from 'react-bootstrap/Alert';
import ButtonToolbar from 'react-bootstrap/ButtonToolbar';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import Row from 'react-bootstrap/Row';
import Table from 'react-bootstrap/Table';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faTrashAlt, faUserEdit, faArrowAltCircleRight } from '@fortawesome/free-solid-svg-icons'
import { any } from 'prop-types';
  
interface MealItem {
    description?: string;
    calories?: number;
}

interface Meal {
    id: string;
    userid?: string;
    filterdate?: number;
    mealtime?: string;
    name?: string;
    description?: string;
    mealItems: MealItem[];
}

interface PossibleFoods {
  possibleFoods: PossibleFood[];
}

interface PossibleFood {
  name: string;
  calories: number;
}

interface IState {
    user?: userData,
    activeDate: Date
    meals: Meal[]
    addEditMeal: Meal,
    mealsToShow: boolean,
    showAddEditModal: boolean,
    addEditModalTitle: string
    addEditDate: Date
    addEditNewItemName: string,
    addEditNewItemCalories: string
  }

class Meals extends React.Component<{},IState> {
    constructor(props, ...state:any) {
        super(props,state);

        this.state = {
          activeDate: new Date(),
          meals: [],
          addEditMeal: {id : "", mealItems: [] },
          mealsToShow: false,
          showAddEditModal: false,
          addEditModalTitle: "",
          addEditDate: new Date(),
          addEditNewItemName: "",
          addEditNewItemCalories: "0"
        };

        this.dealWithDateChange = this.dealWithDateChange.bind(this);
        this.dealWithEditClick = this.dealWithEditClick.bind(this);
        this.dealwithAddEditDateTimechange = this.dealwithAddEditDateTimechange.bind(this);
        this.dealwithAddEditNewItemNameChange = this.dealwithAddEditNewItemNameChange.bind(this);
        this.dealwithAddEditNewItemCaloriesChange = this.dealwithAddEditNewItemCaloriesChange.bind(this);
        this.dealwithAddEditAddMealItem = this.dealwithAddEditAddMealItem.bind(this);
    }

    dealWithDateChange(date) {
      this.setState({activeDate: date})
      let dateToFilter = moment(date).format('MM/DD/YYYY');
      this.getMealList(dateToFilter);
    }

    dealwithAddEditDateTimechange(date) {
      this.setState({addEditDate: date})
    }

    dealwithAddEditAddMealItem(e) {
      // Were calores entered corectly
      if(isNaN(this.state.addEditNewItemCalories as any)){
        this.setState({addEditNewItemCalories: "0"})
        alert("You must enter a number in the calories field")
        return;
      }

      let calories = parseInt(this.state.addEditNewItemCalories);
      if (calories <= 0) {
        // Call the API to get the calorie count
        mealServices.getCaloriesFromAPI(this.state.addEditNewItemName).then(response=>{
          if(response !== undefined) { 
            
            if(response.message !== undefined) {
              alert(`Unable to auto determine calories for ${this.state.addEditNewItemName}`)
              this.setState({
                addEditNewItemName: "",
                addEditNewItemCalories: "0"
              })
              return;
            }

            let resp = response as PossibleFoods; 
            calories = resp.possibleFoods[0].calories;

            let newItem: MealItem = {
              description: this.state.addEditNewItemName,
              calories: calories
            }
            this.state.addEditMeal.mealItems.push(newItem);
            this.setState({
              addEditNewItemName: "",
              addEditNewItemCalories: "0"
            })
          } else {
            alert(`Unable to auto determine calories for ${this.state.addEditNewItemName}`)
          }
        })
        return;
      } 
     
      let newItem: MealItem = {
        description: this.state.addEditNewItemName,
        calories: calories
      }
      this.state.addEditMeal.mealItems.push(newItem);
      this.setState({
        addEditNewItemName: "",
        addEditNewItemCalories: "0"
      })
    }

    dealwithAddEditNewItemNameChange(e){
      this.setState({addEditNewItemName: e.target.value})
    }

    dealwithAddEditNewItemCaloriesChange(e) {
      this.setState({addEditNewItemCalories: e.target.value})
    }

    dealWithEditClick(itemToEdit) {
      mealServices.getMeal(itemToEdit).then(response=>{
        if(response !== undefined) { 
          let meal = response as Meal
          this.setState({
            addEditDate: moment(meal.filterdate).toDate(),
            addEditMeal: meal,
            showAddEditModal: true, 
            addEditModalTitle: "EDIT MEAL"
          })
        }
      })
    }

    componentDidMount() { 
      let dateToFilter = moment(this.state.activeDate).format('MM/DD/YYYY');
      this.getMealList(dateToFilter);
    }

    getMealList(dateToPull) {
      mealServices.getMealList(dateToPull).then(
        response=>{
          if(response !== undefined) {
            this.setState({ 
              meals : response as Meal[],
            });
              this.setState({mealsToShow: (this.state.meals.length > 0)})
          }
      })
    }

    render() {
        return(
          <div>

          <Modal show={this.state.showAddEditModal} backdrop="static" size="lg" aria-labelledby="contained-modal-title-vcenter"
          centered> 
            <Modal.Header>
              <Modal.Title>{this.state.addEditModalTitle}</Modal.Title>
            </Modal.Header>

            <Modal.Body>
              <Form>
              <Form.Group controlId="addEditDate">
              <Form.Label>Date & Time of The Meal</Form.Label><br></br>
              <TimePicker value={this.state.addEditDate} onChange={this.dealwithAddEditDateTimechange}></TimePicker>
            </Form.Group>
                <Form.Group controlId="addEditName">
                  <Form.Label>Name</Form.Label>
                  <Form.Control type="Name" placeholder="Enter Name" value={this.state.addEditMeal.name} />
                  <Form.Text className="text-muted">
                      Short name for the meal
                  </Form.Text>
                </Form.Group>
                <Form.Group controlId="addEditDescription">
                  <Form.Label>Description</Form.Label>
                  <Form.Control type="Name" placeholder="Enter Description" value={this.state.addEditMeal.description} />
                  <Form.Text className="text-muted">
                      More detailed description for meal
                  </Form.Text>
                </Form.Group>
                <Row>
                  <Col>
                    <Form>
                      <Row>
                        <Col>
                          <Form.Control placeholder="Meal Item" value={this.state.addEditNewItemName} onChange={this.dealwithAddEditNewItemNameChange} />
                        </Col>
                        <Col>
                          <Form.Control placeholder="Calories" value={this.state.addEditNewItemCalories} onChange={this.dealwithAddEditNewItemCaloriesChange} />
                        </Col>
                        <Col>
                          <Button variant="primary" onClick={this.dealwithAddEditAddMealItem}><FontAwesomeIcon icon={faArrowAltCircleRight} /></Button>
                        </Col>
                      </Row>
                    </Form>
                  </Col>
                  <Col>
                    <Table striped bordered>
                      <thead>
                        <tr>
                          <td></td>
                          <td>Meal Item</td>
                          <td>Calories</td>
                        </tr>
                      </thead>
                      <tbody>
                        {this.state.addEditMeal.mealItems.map((item) => (
                          <tr>
                            <td><Button variant="primary"><FontAwesomeIcon icon={faTrashAlt} /></Button></td>
                            <td>{item.description}</td>
                            <td>{item.calories}</td>
                          </tr>
                        ))}
                      </tbody>
                    </Table>
                  </Col>
                </Row>           
              </Form>
            </Modal.Body>

            <Modal.Footer>
              <Button variant="secondary">Close</Button>
              <Button variant="primary">Save Changes</Button>
            </Modal.Footer>
          </Modal>

          <Form>
            <Row>
              <Col>
                <Form.Group>
                  <Form.Label>Date To View</Form.Label><br></br>
                  <DatePicker onChange={this.dealWithDateChange} value={this.state.activeDate}></DatePicker>
                  <Form.Text>Sets the active date </Form.Text>
                </Form.Group>
              </Col>
              <Col>
                  <Form.Group>
                  <Form.Label>User To View</Form.Label><br></br>
                  <Form.Control as="select" className="userNameSelection">
                    <option>...</option>
                  </Form.Control>
                  <Form.Text>Allows you to view any user you wish</Form.Text>
                </Form.Group>
              </Col>
            </Row>
          </Form>            
            <Alert variant="info" show={!this.state.mealsToShow} >
              No Meals Found For The Selected Date
            </Alert>
            <table>
              <tbody>  
                {this.state.meals.map((meals) => (
                  <tr>
                    <td className="optionContainer"> 
                      <ButtonToolbar>
                        <Button variant="primary"><FontAwesomeIcon icon={faTrashAlt} /></Button>&nbsp;
                        <Button variant="primary" onClick={itemToEdit=>this.dealWithEditClick(meals.id)}><FontAwesomeIcon icon={faUserEdit} /></Button>
                      </ButtonToolbar>
                    </td>
                    <td>            
                      <div key={meals.id.toString()}>
                          <span className="timeBlock"><Moment format="hh:mm A">{meals.filterdate}</Moment></span>
                          <span className="mealName">{meals.name}</span>
                      </div>
                      <div>
                        <ul>
                        {meals.mealItems.map((items)=> (
                            <li>{items.description} [{items.calories}]</li>
                          ))}
                        </ul>
                      </div>
                    </td>
                  </tr>
                ))}

              </tbody>
            </table>

          <Button variant="primary">Add Additional Meal</Button>

          </div>
        )
    }

}

export { Meals };