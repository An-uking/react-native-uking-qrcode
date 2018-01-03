import { PropTypes } from 'prop-types';
import { requireNativeComponent, View } from 'react-native';

var BarCode = {
    name: 'RCTBarCodeImageView',
    propTypes: {
        ...View.propTypes,
        options: PropTypes.shape({
            code: PropTypes.string.isRequired,
            format: PropTypes.string,
            height: PropTypes.number,       //only supports android
            width: PropTypes.number,     //only supports android
        }).isRequired,
    },
};
module.exports = requireNativeComponent('RCTBarCode', BarCode);