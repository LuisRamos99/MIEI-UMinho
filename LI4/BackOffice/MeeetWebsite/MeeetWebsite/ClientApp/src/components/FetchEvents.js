import React, { Component } from 'react';

export class FetchEvents extends Component {
    static displayName = FetchEvents.name;

    constructor(props) {
        super(props);
        this.state = {
            events: [],
            users: [],
            idUserEvent: "",
            nome: "",
            latitude: "",
            longitude: "",
            idAdmin: "",
            id: "",
            idUser: "",
            idEvent: "",
            loading: true,
            waiting: true
        };
    }

    async addEvent(nomeText, LatitudeFloat, LongitudeFloat, idInt) {
        const event = {
            "nome": nomeText,
            "dataHora": "2020-01-01",
            "longitude": parseFloat(LongitudeFloat),
            "latitude": parseFloat(LatitudeFloat),
            "tipoEvento": 0,
            "idAdmin": Number(idInt),
            "descricao": null,
            "idadeMinima": null,
            "idAdminNavigation": null,
            "eventoHasRequests": null,
            "utilizadorEvento": null,
            "votacao":null
        }

        await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/PostEvento', {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(event)
        }).then((update) => { this.populateEvents() });

    }

    async addUser(id_user, id_event) {
        const data = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getevento/' + id_event, { mode: 'cors' });
        const event = await data.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/addToEvent/' + id_user, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(event)
        }).then((update) => { this.populateEvents() });

    }

    async removeEvent(idInt) {

        const dataUserEvents = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getusereventosperevent/' + idInt, { mode: 'cors' });
        const userEvents = await dataUserEvents.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteuserEventos', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userEvents)
        });

        const dataUserOpcao = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUserOpcaoperevent/' + idInt, { mode: 'cors' });
        const userUserOpcao = await dataUserOpcao.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteUserOpcao', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userUserOpcao)
        });

        const dataOpcao = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getOpcaoperevent/' + idInt, { mode: 'cors' });
        const userOpcao = await dataOpcao.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteOpcao', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userOpcao)
        });

        const dataVotacao = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getVotacaoperevent/' + idInt, { mode: 'cors' });
        const userVotacao = await dataVotacao.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteVotacao', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userVotacao)
        });

        const dataRequests = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getEventoHasRequests/' + idInt, { mode: 'cors' });
        const userRequests = await dataRequests.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteEventoRequests', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userRequests)
        });

        const dataEvent = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getevento/' + idInt, { mode: 'cors' });
        const event = await dataEvent.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteEvento', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(event)
        }).then((update) => { this.populateEvents() });

    }

    componentDidMount() {
        this.populateEvents();
    }

    handleNomeChange(event) {
        this.setState({ nome: event.target.value })
    }

    handleLatitudeChange(event) {
        this.setState({ latitude: event.target.value })
    }

    handleLongitudeChange(event) {
        this.setState({ longitude: event.target.value })
    }

    handleIdAdminChange(event) {
        this.setState({ idAdmin: event.target.value })
    }

    handleIdChange(event) {
        this.setState({ id: event.target.value })
    }

    handleIdUserChange(event) {
        this.setState({ idUser: event.target.value })
    }

    handleIdEventChange(event) {
        this.setState({ idEvent: event.target.value })
    }

    handleIdUserEventChange(event) {
        this.setState({ idUserEvent: event.target.value })
    }

    static renderEventsTable(events) {
        return (
            <table className='table table-striped' aria-labelledby="tabelLabel">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Nome</th>
                        <th>Data Hora</th>
                        <th>Longitude</th>
                        <th>Latitude</th>
                        <th>Id Admin</th>
                    </tr>
                </thead>
                <tbody>
                    {events.map(event =>
                        <tr key={event.id}>
                            <td>{event.id}</td>
                            <td>{event.nome}</td>
                            <td>{event.dataHora}</td>
                            <td>{event.longitude}</td>
                            <td>{event.latitude}</td>
                            <td>{event.idAdmin}</td>
                        </tr>
                    )}
                </tbody>
            </table>
        );
    }

    static renderUsersTable(users) {
        return (
            <table className='table table-striped' aria-labelledby="tabelUsers">
                <thead>
                    <tr>
                        <th>Username</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map(user =>
                        <tr key={user.id}>
                            <td>{user.username}</td>
                        </tr>
                    )}
                </tbody>
            </table>
        );
    }

    render() {
        let contents = this.state.loading
            ? <p><em>Loading...</em></p>
            : FetchEvents.renderEventsTable(this.state.events);

        let userEventsTable = this.state.waiting
            ? <p><em>Waiting...</em></p>
            : FetchEvents.renderUsersTable(this.state.users);

        return (
            <div>
                <h1 id="tabelLabel" >All Events</h1>
                <p>This component demonstrates fetching data from the server.</p>
                {contents}
                <label>
                Nome:
                <input type="text" value={this.state.nome} onChange={this.handleNomeChange.bind(this)} />
                </label>
                <label>
                    Latitude:
                <input type="text" value={this.state.latitude} onChange={this.handleLatitudeChange.bind(this)} />
                </label>
                <label>
                    Longitude:
                <input type="text" value={this.state.longitude} onChange={this.handleLongitudeChange.bind(this)} />
                </label>
                <label>
                    Id de Admin:
                <input type="number" value={this.state.idAdmin} onChange={this.handleIdAdminChange.bind(this)} />
                </label>
                <button onClick={(e) => { this.addEvent(this.state.nome, this.state.latitude, this.state.longitude, this.state.idAdmin); }}>Add Event</button>

                <label>
                    Id de Evento:
                <input type="number" value={this.state.idEvent} onChange={this.handleIdEventChange.bind(this)} />
                </label>
                <label>
                    Id de User:
                <input type="number" value={this.state.idUser} onChange={this.handleIdUserChange.bind(this)} />
                </label>
                <button onClick={(e) => { this.addUser(this.state.idUser, this.state.idEvent); }}>Add User to Event</button>

                <label>
                    Id:
                <input type="number" value={this.state.id} onChange={this.handleIdChange.bind(this)} />
                </label>
                <button onClick={(e) => { this.removeEvent(this.state.id); }}>Remove Event</button>

                <h1>__________________________________</h1>

                <h1 id="tabelUsers" >All Users participating in Event</h1>
                {userEventsTable}

                <label>
                    Id de Event:
                <input type="number" value={this.state.idUserEvent} onChange={this.handleIdUserEventChange.bind(this)} />
                </label>
                <button onClick={(e) => { this.populateUsers(this.state.idUserEvent); }}>See Users in this Event</button>
            </div>
        );
    }

    async populateEvents() {
        const response = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/geteventos', { mode: 'cors' });
        const data = await response.json();
        console.log(data);
        this.setState({ events: data, loading: false });
    }

    async populateUsers(idInt) {
        const response = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUserEventosPerEvent/' + idInt, { mode: 'cors' });
        const data = await response.json();

        const dataUsers = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUsersPerEvent/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        const usersList = await dataUsers.json();
        console.log(usersList);
        this.setState({ users: usersList, waiting: false });
    }
}
