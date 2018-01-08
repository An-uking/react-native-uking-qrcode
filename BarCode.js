import { PropTypes } from 'prop-types';
import { requireNativeComponent, View } from 'react-native';

var BarCode = {
    name: 'RCTBarCodeImageView',
    propTypes: {
        ...View.propTypes,
        options: PropTypes.shape({
            code: PropTypes.string.isRequired,
            format: PropTypes.string,
            height: PropTypes.number,
            width: PropTypes.number,
        }).isRequired,
    },
};
module.exports = requireNativeComponent('RCTBarCode', BarCode);
