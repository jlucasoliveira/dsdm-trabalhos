const { CarroModel } = require("../models/CarroModel");

let _id = 0;
let carros = [];

class CarroService {

    static list() {
        return carros;
    }

    static retrieve(id) {
        for (var carro of carros)
            if (carro.id == id)
                return carro;
        return null;
    }

    static register(data) {
        const carro = new CarroModel(
            _id++,
            data.modelo,
            data.marca,
            data.cor,
            data.anoDeLancamento,
        );

        carros.push(carro);
        return carro;
    }

    static update(id, data) {
        for (var carro of carros)
            if (carro.id == id) {
                carro.modelo = data.modelo;
                carro.marca = data.marca;
                carro.cor = data.cor;
                carro.anoDeLancamento = data.anoDeLancamento;
                return carro;
            }
        return null;
    }

}

module.exports = { CarroService };