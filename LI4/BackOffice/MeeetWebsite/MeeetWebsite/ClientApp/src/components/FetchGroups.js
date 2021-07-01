import React, { Component } from 'react';

export class FetchGroups extends Component {
    static displayName = FetchGroups.name;

    constructor(props) {
        super(props);
        this.state = {
            groups: [],
            id: "",
            nome: "",
            idGrupo: "",
            idUser: "",
            loading: true
        };
    }

    componentDidMount() {
        this.populateGroups();
    }

    handleNomeChange(event) {
        this.setState({ nome: event.target.value })
    }

    handleIdChange(event) {
        this.setState({ id: event.target.value })
    }

    handleIdGrupoChange(event) {
        this.setState({ idGrupo: event.target.value })
    }

    handleIdUserChange(event) {
        this.setState({ idUser: event.target.value })
    }

    addGroup(nomeText) {
        const grupo = {
            "nome": nomeText,
            "utilizadorGrupo": null
        }

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/PostGrupo', {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(grupo)
        }).then((update) => { this.populateGroups() });

    }

    async addUser(id_user, id_grupo) {
        const data = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getgrupo/' + id_grupo, { mode: 'cors' });
        const grupo = await data.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/addToGroup/' + id_user, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(grupo)
        }).then((update) => { this.populateGroups() });

    }

    async removeGroup(idInt) {

        const dataGroup = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUserGruposPerGroup/' + idInt, { mode: 'cors' });
        const userGroup = await dataGroup.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteUserGrupos', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userGroup)
        });

        const data = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getgrupo/' + idInt, { mode: 'cors' });
        const group = await data.json();

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteGrupo', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(group)
        }).then((update) => { this.populateGroups() });

    }

    static renderGroupsTable(groups) {
        return (
            <table className='table table-striped' aria-labelledby="tabelLabel">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Nome</th>
                    </tr>
                </thead>
                <tbody>
                    {groups.map(group =>
                        <tr key={group.id}>
                            <td>{group.id}</td>
                            <td>{group.nome}</td>
                        </tr>
                    )}
                </tbody>
            </table>
        );
    }

    render() {
        let contents = this.state.loading
            ? <p><em>Loading...</em></p>
            : FetchGroups.renderGroupsTable(this.state.groups);

        return (
            <div>
                <h1 id="tabelLabel" >All Groups</h1>
                <p>This component demonstrates fetching data from the server.</p>
                {contents}
                <label>
                    Nome:
                <input type="text" value={this.state.nome} onChange={this.handleNomeChange.bind(this)} />
                </label>
                <button onClick={(e) => { this.addGroup(this.state.nome); }}>Add Group</button>

                <label>
                    Id de Grupo:
                <input type="number" value={this.state.idGrupo} onChange={this.handleIdGrupoChange.bind(this)} />
                </label>
                <label>
                    Id de User:
                <input type="number" value={this.state.idUser} onChange={this.handleIdUserChange.bind(this)} />
                </label>
                <button onClick={(e) => { this.addUser(this.state.idUser, this.state.idGrupo); }}>Add User to Group</button>

                <label>
                    Id:
                <input type="number" value={this.state.id} onChange={this.handleIdChange.bind(this)} />
                </label>
                <button onClick={(e) => { this.removeGroup(this.state.id); }}>Remove Group</button>
            </div>
        );
    }

    async populateGroups() {
        const response = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet', { mode: 'cors' });
        const data = await response.json();
        console.log(data);
        this.setState({ groups: data, loading: false });
    }
}
