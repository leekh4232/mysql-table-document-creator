#!/usr/bin/env node

import shelljs from "shelljs";
import minimist from "minimist";
import commandLineArgs from "command-line-args";
import createTableDocument from "./createTableDocument.js";

const optionDefinitions = [
  { name: 'host', alias: 'h', type: String },
  { name: 'port', type: Number },
  { name: 'user', alias: 'u', type: String },
  { name: 'password', alias: 'p', type: String },
  { name: 'database', alias: 'd', type: String },
  { name: 'output', alias: 'o', type: String }
]

const options = commandLineArgs(optionDefinitions);

// 현재 작업 디렉토리
const cwd = shelljs.pwd().toString();


// DATABASE 연동정보 설정
const env = {
    host : options['host'] || "127.0.0.1",
    port : options['port'] || 9090,
    user : options['user'] || "root",
    password : options['password'] || "1234",
    database : options['database'] || "myschool",
    output : options['output'] || cwd,
    connectionLimit: 10,
    connectTimeout: 30000,
    waitForConnections: true
};

// console.log(env);
// process.exit();

// 프로그램 시작
console.clear();
console.log("================================================");
console.log("|         MySQL DATABASE Util (by leekh)       |");
console.log("================================================");

for (let key in env) {
    console.log(`- ${key}: ${env[key]}`);
}


(async () => {
    try {
        await createTableDocument(env);
    } catch (err) {
        console.error(err);
    } finally {
        process.exit(1);
    }
})();
