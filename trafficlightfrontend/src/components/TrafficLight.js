import React from 'react'
import './TrafficLight.css'

export default class TrafficLight extends React.Component{

    render(){
        return (
            <div>
                <div className="lightsWrapper">
                    <div className="redWrapper">
                    {this.props.red ? <div className="redLightOnOverlay"/> :  null}
                        <div className="redLightOff"/>
                    </div>
                    <div className="amberWrapper">
                        {this.props.amber ?<div className="amberLightOnOverlay"/> : null}
                        <div className="amberLightOff"/>
                    </div>
                    <div className="greenWrapper">
                        {this.props.green ?<div className="greenLightOnOverlay"/> : null}
                        <div className="greenLightOff"/>
                    </div>
                </div>
                <div className="trafficPole"/>
            </div>
        );
    }

}