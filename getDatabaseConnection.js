#!/usr/bin/env node

import DBPoolHelper from "./DBPoolHelper.js";

const getDatabaseConnection = async (connectionInfo) => {
    const dbcon = await DBPoolHelper.getInstance(connectionInfo).getConnection();
    return dbcon;
};

export default getDatabaseConnection;