import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {requireNativeComponent, View, processColor, StyleSheet} from 'react-native';
import { QRScanner } from 'react-native-uking-qrcode';

const SCANNER_REF = 'scanner';

function convertNativeProps(props) {
    const newProps = {
        ...props
    };
    if (typeof props.maskColor === 'string') {
        newProps.maskColor = processColor(props.maskColor);
    }
    if (typeof props.cornerColor === 'string') {
        newProps.cornerColor = processColor(props.cornerColor);
    }
    if (typeof props.scanLineColor === 'string') {
        newProps.scanLineColor = processColor(props.scanLineColor);
    }
    if (typeof props.borderColor === 'string') {
        newProps.borderColor = processColor(props.borderColor);
    }
    if (typeof props.tipTextColor === 'string') {
        newProps.tipTextColor = processColor(props.tipTextColor);
    }
    if (typeof props.tipBackgroundColor === 'string') {
        newProps.tipTextColor = processColor(props.tipBackgroundColor);
    }

    delete newProps.onSuccess;
    delete newProps.onError;

    return newProps;
}

class QRScanner extends Component {
    static propTypes = {
        ...View.propTypes,
        topOffset: PropTypes.number,
        cornerSize: PropTypes.number,
        cornerLength: PropTypes.number,
        scanLineSize: PropTypes.number,
        rectWidth: PropTypes.number,
        maskColor: PropTypes.string,
        cornerColor: PropTypes.string,
        scanLineColor: PropTypes.string,
        scanLineMargin: PropTypes.number,
        borderSize: PropTypes.number,
        borderColor: PropTypes.string,
        animTime: PropTypes.number,
        centerVertical: PropTypes.bool,
        toolbarHeight: PropTypes.number,
        barcodeRectHeight: PropTypes.number,
        isBarCode: PropTypes.bool,
        barCodeTipText: PropTypes.string,
        qrCodeTipText: PropTypes.string,
        tipTextSize: PropTypes.number,
        tipTextColor: PropTypes.string,
        tipTextBelowRect: PropTypes.number,
        tipTextMargin: PropTypes.number,
        showTipBackground: PropTypes.bool,
        tipBackgroundColor: PropTypes.string,
        scanLineReverse: PropTypes.bool,
        showDefaultGridScanLineDrawable: PropTypes.bool,
        onlyDecodeScanBoxArea: PropTypes.bool,
        delay: PropTypes.number,

        onSuccess: PropTypes.func,
        onError: PropTypes.func
    };

    _onSuccess = (event : Event) => {
        if (!this.props.onSuccess) {
            return;
        }
        this.props.onSuccess(event.nativeEvent.result);
    }

    _onError = (event : Event) => {
        if (!this.props.onError) {
            return;
        }
        this.props.onError(event.nativeEvent.error);
    }

    render() {
        const style = [styles.base, this.props.style];
        const nativeProps = convertNativeProps(this.props);

        return <RCTScanner
            {...nativeProps}
            onSuccess={this._onSuccess}
            onScanError={this._onError}/>;
    }
}

const RCTScanner = requireNativeComponent('RCTScanner', QRScanner);

export default RCTScanner;

//const styles = StyleSheet.create({base: {}});