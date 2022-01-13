import json

import flask
from flask import jsonify, request

app = flask.Flask(__name__)
app.config["DEBUG"] = True


@app.route("/login", methods=["POST"])
def login():
    data = {"success": True}

    return jsonify(data)


@app.route("/formData/CustomerCRM/", methods=["GET"])
def customerCRM():
    data = {
        "success": True,
        "data": {
            "field": {
                "accountid": [
                    "208329",
                ],
                "accountnumber": [
                    "55889",
                ],
            }
        },
    }

    return jsonify(data)


@app.route("/widget._GenericProcess", methods=["GET", "POST"])
def accountTransactions():
    if request.method == "GET":
        return jsonify({"success": True})

    request_data = json.loads(request.data)

    if request_data["actions"][1] == "getCustomerAccounts":
        data = {
            "success": True,
            "data": {
                "fieldValue": {
                    "resultList": {
                        "id": [[ "208329", ],],
                    }
                }
            },
        }
    elif request_data["actions"][1] == "getAccountTransactions":
        data = {"success": True}

    return jsonify(data)


app.run()
