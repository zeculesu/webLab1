const points = [];

function draw(radius) {
    const canvas = document.getElementById('graphCanvas');
    const ctx = canvas.getContext('2d');

    const width = canvas.width;
    const height = canvas.height;

    clearCanvas(ctx, width, height);

    const centerX = width / 2;
    const centerY = height / 2;

    const R = radius * width / 6 / 2;

    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.arc(centerX, centerY, R, 0.5 * Math.PI, Math.PI);
    ctx.lineTo(centerX, centerY);
    ctx.closePath();
    ctx.fillStyle = '#25CED1';
    ctx.fill();

    ctx.beginPath();
    ctx.rect(centerX, centerY - R, R / 2, R);
    ctx.closePath();
    ctx.fillStyle = '#25CED1';
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX - R / 2, centerY);
    ctx.lineTo(centerX, centerY - R / 2);
    ctx.closePath();
    ctx.fillStyle = '#25CED1';
    ctx.fill();

    ctx.strokeStyle = 'black';
    ctx.lineWidth = 2;
    ctx.beginPath();
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX, height);
    ctx.moveTo(0, centerY);
    ctx.lineTo(width, centerY);
    ctx.stroke();

    ctx.lineWidth = 1;
    const step = width / 6 / 2;
    for (let i = 1; i <= 11; i++) {
        ctx.beginPath();
        ctx.moveTo(i * step, centerY - 5);
        ctx.lineTo(i * step, centerY + 5);
        ctx.stroke();

        ctx.beginPath();
        ctx.moveTo(centerX - 5, i * step);
        ctx.lineTo(centerX + 5, i * step);
        ctx.stroke();
    }
    ctx.lineWidth = 1.5;
    ctx.beginPath();
    ctx.moveTo(centerX - 8, 10);
    ctx.lineTo(centerX, 1);
    ctx.lineTo(centerX + 8, 10);
    ctx.stroke();

    ctx.lineWidth = 1.5;
    ctx.beginPath();
    ctx.moveTo(centerX * 2 - 10, centerY - 8);
    ctx.lineTo(centerX * 2 - 1, centerY);
    ctx.lineTo(centerX * 2 - 10, centerY + 8);
    ctx.stroke();

    ctx.font = '12px Arial';
    ctx.fillStyle = 'black';
    for (let i = -5; i <= 5; i++) {
        if (i !== 0) {
            ctx.fillText(i, centerX + i * step - 5, centerY + 20);
            ctx.fillText(-i, centerX - 20, centerY + i * step + 5);
        }
    }
    ctx.font = 'bold 12px Arial';
    ctx.fillText("X", centerX + 5.8 * step - 5, centerY + 20);
    ctx.fillText("Y", centerX - 20, centerY - 5.8 * step + 5);

    points.forEach(point => {
        drawPoint(point.x, point.y);
    });
}

function clearCanvas(ctx, width, height) {
    ctx.clearRect(0, 0, width, height);
}

function drawPoint(x, y) {
    const canvas = document.getElementById('graphCanvas');
    const ctx = canvas.getContext('2d');
    const width = canvas.width;
    const height = canvas.height;
    const centerX = width / 2;
    const centerY = height / 2;
    const step = width / 6 / 2;

    let circle = new Path2D();
    circle.arc(centerX + x * step, centerY - y * step, 3, 0, 2 * Math.PI);
    ctx.fillStyle = "#EA526F";
    ctx.fill(circle);

    points.push({x: x, y: y});
}
