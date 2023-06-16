import React from 'react'
import TemporaryDrawer from '../../common/navbar';
import { Container } from '@mui/material';
import CustomPaginationActionsTable from '../Recent Orders/recentOrders';

export const AdminHome = () =>{
    return(
        <div>
            <TemporaryDrawer/>
            <div className='main-body'>
                <Container fluid sx={{display: { xs: 'none', md: 'flex' }, ml: 12}}>
                    <CustomPaginationActionsTable/>
                </Container>
            </div>
        </div>
    );
}