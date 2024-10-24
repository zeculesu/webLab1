function handleSubmit() {
    const xInput = document.getElementById("x").value;
    const yInput = document.querySelector('input[name="y"]').value;
    const rInput = document.querySelector('input[name="radius"]:checked').value;

    const yCheck = checkY(yInput);
    const xCheck = checkX(xInput);
    const rCheck = checkR(rInput);

    if (yCheck && xCheck && rCheck) {
        const yValue = parseFloat(yInput);
        const xValue = parseFloat(xInput);
        const rValue = parseInt(rInput);

        const requestContent = {
            "method": "post",
            "headers": {
                "content-type": "application/json",
            },
            "body": JSON.stringify({
                x: xValue,
                y: yValue,
                r: rValue
            })

        };
        const startTime = Date.now();
        const url = "http://localhost:8080/fcgi-bin/web_server.jar";
        fetch(url, requestContent)
            .then(response => response.json())
            .then(data => {
                const endTime = Date.now();
                const duration = endTime - startTime;
                drawPoint(data.x, data.y);
                const time = new Date().toLocaleTimeString();
                const hitResult = data.result;
                const x = data.x;
                const y = data.y;
                const r = data.r;
                addNewLineTable(time, x, y, r, hitResult, duration);
            });
    }
}

function addNewLineTable(time, x, y, r, hitResult, executionTime) {
    const table = document.getElementById('resultTable');
    const newRow = document.createElement('tr');
    const rowData = [time, x, y, r, hitResult, executionTime];

    rowData.forEach((data, index) => {
        const newCell = document.createElement('td');
        newCell.textContent = data;

        if (index === 4) {
            newCell.style.color = hitResult === "true" ? "#25CED1" : "#EA526F";
        }

        newRow.appendChild(newCell);
    });

    table.insertBefore(newRow, table.rows[1]);
}
