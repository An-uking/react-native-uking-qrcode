import { PropTypes } from 'prop-types';
import { requireNativeComponent, View } from 'react-native';

var BarCode = {
    name: 'RCTScanner',
    propTypes: {
        onSuccess:PropTypes.bool
    },
};
module.exports = requireNativeComponent('RCTScanner', BarCode);