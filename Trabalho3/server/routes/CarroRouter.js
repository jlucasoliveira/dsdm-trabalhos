const { Router } = require("express");
const { CarroService } = require("../services/CarroService");

const router = Router();

router.get('/', (req, res) => {
    return res.json(CarroService.list());
});

router.get('/:id', (req, res) => {
    const id = req.params.id;
    const carro = CarroService.retrieve(id);
    if (carro == null) return res.status(400).send();
    return res.json( carro );
});

router.post('/', (req, res) => {
    const data = req.body;
    return res.status(201).json(CarroService.register(data));
});

router.put('/:id', (req, res) => {
    const id = req.params.id;
    const data = req.body;
    const carro = CarroService.update(id, data);
    if (carro == null) return res.status(400).send();
    return res.json(carro);
});


module.exports = { CarroRouter: router };