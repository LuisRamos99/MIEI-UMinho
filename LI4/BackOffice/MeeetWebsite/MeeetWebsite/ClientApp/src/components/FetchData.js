import React, { Component } from 'react';

export class FetchData extends Component {
  static displayName = FetchData.name;

  constructor(props) {
    super(props);
      this.state = {
          users: [],
          events: [],
          id: "",
          idUser: "",
          username: "",
          email: "",
          password: "",
          morada: "",
          loading: true,
          waiting: true
      };
    }

  componentDidMount() {
    this.populateUsers();
    }

  addUser(usernameText,emailText,passwordText,cityText) {
      const user = {
          "username": usernameText,
          "email": emailText,
          "password": passwordText,
          "longitude": 0,
          "latitude": 0,
          "urlFoto": "https://i0.wp.com/ipc.digital/wp-content/uploads/2016/07/icon-user-default.png?fit=462%2C462&ssl=1",
          "morada": cityText,
          "dataNascimento": "2014-01-01",
          "genero": null,
          "bio": null,
          "amigo": null,
          "evento": null,
          "utilizadorConvites": null,
          "utilizadorEvento": null,
          "utilizadorGrupo": null,
          "utilizadorOpcao": null,
          "utilizadorPedidosAmizade": null
      }

      fetch('https://meeet-projeto.azurewebsites.net/api/meeet/PostUser', {
          method: 'POST',
          headers: {
              Accept: 'application/json',
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(user)
      }).then((update) => { this.populateUsers() });

    }

    async removeUser(idInt) {

        const dataAmigos = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getamigosuser/' + idInt, { mode: 'cors' });
        const userAmigos = await dataAmigos.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteAmigos', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userAmigos)
        });

        const dataGrupos = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getusergruposperuser/' + idInt, { mode: 'cors' });
        const userGrupos = await dataGrupos.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteUserGrupos', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userGrupos)
        });

        const dataPedidos = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getalluserpedidosamizade/' + idInt, { mode: 'cors' });
        const userPedidos = await dataPedidos.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteAllUserPedidosAmizade', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userPedidos)
        });

        const dataConvites = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getalluserconvites/' + idInt, { mode: 'cors' });
        const userConvites = await dataConvites.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteUserConvites', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userConvites)
        });

        const dataUserEvento = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getusereventosPerUser/' + idInt, { mode: 'cors' });
        const userUserEvento = await dataUserEvento.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteUserEventos', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userUserEvento)
        });

        const dataEvento = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/geteventosPerAdmin/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userUserEvento)
        });
        const userEvento = await dataEvento.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteEventosAdmin', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userEvento)
        });

        const dataOpcao = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getuseropcaoperuser/' + idInt, { mode: 'cors' });
        const userOpcao = await dataOpcao.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteUserOpcao', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userOpcao)
        });

        const data = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getuser/' + idInt, { mode: 'cors' });
        const user = await data.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteUser', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        }).then((update) => { this.populateUsers() });

    }

    handleUsernameChange(event) {
        this.setState({ username: event.target.value })
    }

    handleEmailChange(event) {
        this.setState({ email: event.target.value })
    }

    handlePasswordChange(event) {
        this.setState({password: event.target.value })
    }

    handleMoradaChange(event) {
        this.setState({ morada: event.target.value })
    }

    handleIdChange(event) {
        this.setState({ id: event.target.value })
    }

    handleIdUserChange(event) {
        this.setState({ idUser: event.target.value })
    }

  static renderUsersTable(users) {
    return (
      <table className='table table-striped' aria-labelledby="tabelLabel">
        <thead>
          <tr>
            <th>Id</th>
            <th>Username</th>
            <th>Email</th>
            <th>Password</th>
            <th>Latitude</th>
            <th>Longitude</th>
            <th>Morada</th>
          </tr>
        </thead>
        <tbody>
          {users.map(user =>
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.username}</td>
              <td>{user.email}</td>
              <td>{user.password}</td>
              <td>{user.latitude}</td>
              <td>{user.longitude}</td>
              <td>{user.morada}</td>
            </tr>
          )}
        </tbody>
      </table>
    );
    }

    static renderEventsTable(events) {
        return (
            <table className='table table-striped' aria-labelledby="tabelEvents">
                <thead>
                    <tr>
                        <th>Nome</th>
                    </tr>
                </thead>
                <tbody>
                    {events.map(event =>
                        <tr key={event.id}>
                            <td>{event.nome}</td>
                        </tr>
                    )}
                </tbody>
            </table>
        );
    }

  render() {
      let contents = this.state.loading
          ? <p><em>Loading...</em></p>
          : FetchData.renderUsersTable(this.state.users);

      let userEventsTable = this.state.waiting
          ? <p><em>Waiting...</em></p>
          : FetchData.renderEventsTable(this.state.events);


      return (
          <div>
              <h1 id="tabelLabel" >All Users</h1>
              <p>This component demonstrates fetching data from the server.</p>
              {contents}
              <label>
              Username:
              <input type="text" value={this.state.username} onChange={this.handleUsernameChange.bind(this)} />
              </label>
              <label>
              Email:
              <input type="text" value={this.state.email} onChange={this.handleEmailChange.bind(this)} />
              </label>
              <label>
              Password:
              <input type="text" value={this.state.password} onChange={this.handlePasswordChange.bind(this)} />
              </label>
              <label>
              Morada:
              <input type="text" value={this.state.morada} onChange={this.handleMoradaChange.bind(this)} />
              </label>
              <button onClick={(e) => { this.addUser(this.state.username, this.state.email, this.state.password,this.state.morada); }}>Add User</button>

              <label>
              Id:
              <input type="number" value={this.state.id} onChange={this.handleIdChange.bind(this)} />
              </label>
              <button onClick={(e) => { this.removeUser(this.state.id); }}>Remove User</button>

              <h1>______________________</h1>

              <h1 id="tabelEvents" >All Events from User</h1>
              {userEventsTable}

              <label>
                  Id de user:
              <input type="number" value={this.state.idUser} onChange={this.handleIdUserChange.bind(this)} />
              </label>
              <button onClick={(e) => { this.populateEvents(this.state.idUser); }}>See Events from this User</button>
          </div>
      );
    }

    async populateEvents(idInt) {
        const response = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUserEventosPerUser/' + idInt, { mode: 'cors' });
        const data = await response.json();

        const dataEvents = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getEventosPerUser/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        const eventsList = await dataEvents.json();
        console.log(eventsList);
        this.setState({ events: eventsList, waiting: false });
    }

    async populateUsers() {
        const response = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getusers', { mode: 'cors' });
        const data = await response.json();
        console.log(data);
        this.setState({ users: data, loading: false });
    }
}
