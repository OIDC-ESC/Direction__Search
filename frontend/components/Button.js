import React from 'react';
import styled from 'styled-components/native';

const Container = styled.TouchableOpacity`
    height: 50px;
    background: #000000;
    justify-content: center;
    align-items: center;
    margin: 3px;
    border-radius: 5px;
`;

const Label = styled.Text`
    font-size: 16px;
    font-weight: bold;
    color: #ffffff;
`;

function Button(props) {
    return (
        <Container onPress={ props.onPress }>
            <Label>{props.children}</Label>
        </Container>
    )
}

export default Button;