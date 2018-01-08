import { PropTypes } from 'prop-types';
import React,{Component } from 'react';
import { requireNativeComponent,View } from 'react-native';

class BarCode extends Component{
    static propTypes={
        ...View.propTypes,
        text: PropTypes.string.isRequired,
        format:PropTypes.oneOf([
            'AZTEC', 'CODABAR', 'CODE_39', 'CODE_93',
            'CODE_128', 'DATA_MATRIX', 'EAN_8',
            'EAN_13', 'ITF', 'MAXICODE', 'PDF_417',
            'QR_CODE', 'RSS_14', 'RSS_EXPANDED',
            'UPC_A', 'UPC_E', 'UPC_EAN_EXTENSION']).isRequired,
        height:PropTypes.number,
        width:PropTypes.number
    }
    static defaultProps={
        height:100,
        width:300,
        format:'EAN_13'
    };
    constructor(props,content){
        super(props,content)

    }
    render(){
        return(
            <RCTBarCode />
        )
    }
}
const RCTBarCode=requireNativeComponent('RCTBarCode', BarCode);
module.exports = RCTBarCode