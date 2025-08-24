#!/usr/bin/env node

import { Table } from "console-table-printer";

const message = (msg, ms = 500, table = false) => {
    if (table) {
        const p = new Table();

        for (let v of msg) {
            p.addRow(v);
        }

        p.printTable();
    } else {
        console.log(msg);
    }

    return new Promise((resolve) => {
        setTimeout(resolve, ms);
    });
};

export default message;