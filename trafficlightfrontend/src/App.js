import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import TrafficLight from './components/TrafficLight'
class App extends Component {
    constructor () {
        super();
        this.state = {
            green: true,
            amber: false,
            red: false,
        }
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick() {
        let localstate = {green : false, amber: false, red: false};

        if(this.state.green == true){
            localstate.amber = true ;
            // console.log("WAsGREEN", localstate)
        }else if (this.state.amber == true){
           localstate.red = true;
        //    console.log("Wasamber",localstate)

        } else if (this.state.red == true){
            localstate.green = true;
            // console.log("WAsRED", localstate)

        }
        this.setState(localstate);
    }

    render() {
        return (
            <div>
                <header>
                    <h1 className="App-title">{this.state.message}</h1>
                </header>
              <button className="bestButton" onClick={this.handleClick}>Change Lights </button>
              <TrafficLight green={this.state.green} amber={this.state.amber} red={this.state.red}/>
            </div>
        );
    }
}

export default App;