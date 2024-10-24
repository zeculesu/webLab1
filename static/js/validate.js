function checkY(y) {
    if (y === null || y === "" || isNaN(y)) {
        alert("Введите корректное число для Y в промежутке от -5 до 5");
        return false;
    }
    const yNumber = parseFloat(y);
    if (isNaN(yNumber)) {
        alert("Y должно быть числом!");
        return false;
    }
    if (yNumber <= -5 || yNumber >= 5) {
        alert("Значение Y должно быть в диапазоне от -5 до 5");
        return false;
    }
    return true;
}

function checkX(x) {
    if (x === null || x === "" || isNaN(x)) {
        alert("Выберите значение для X!");
        return false;
    }
    const xNumber = parseInt(x);
    if (isNaN(xNumber)) {
        alert("X должно быть числом!");
        return false;
    }
    if (!(xNumber === -2 || xNumber === -1.5 || xNumber === -1 || xNumber === 0 || xNumber === 1 || xNumber === 1.5 || xNumber === 2)) {
        alert("Значение X должно быть в диапазоне от -2 до 2");
        return false;
    }
    return true;
}

function checkR(r) {
    if (r === null) {
        alert("Выберите значение для R!");
        return false;
    }
    const rNumber = parseInt(r);
    if (isNaN(rNumber)) {
        alert("R должно быть числом!");
        return false;
    }
    if (rNumber < 1 || rNumber > 5) {
        alert("Значение R должно быть в диапазоне от 1 до 5");
        return false;
    }
    return true;
}
