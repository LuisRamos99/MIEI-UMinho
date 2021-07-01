using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using TodoApi.DB;

namespace TodoApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class MeeeTController : ControllerBase
    {

        private meeetdbContext _context;
        public MeeeTController(meeetdbContext context)
        {
            _context = context;
        }


        /*******\
       | * GET * | ---------------------------------------------------------------------------------------------------------------
        \*******/

        // Todos os grupos na database
        // GET: api/MeeeT
        [HttpGet]
        public List<Grupo> Get()
        {
            return _context.Grupo.ToList();
        }

        // Se um utilizador está a participar num evento
        // GET: api/MeeeT/isInEvent/id_user/id_event
        [Route("isInEvent/{id_user:int}/{id_evento:int}")]
        [HttpGet]
        public bool IsInEvent(int id_user, int id_evento)
        {
            foreach (var t in _context.UtilizadorEvento)
            {
                return t.IsMember(id_user, id_evento);
            }
            return false;
        }


        // Todos os utilizadores na database
        // GET: api/MeeeT/getUsers
        [Route("getUsers")]
        [HttpGet]
        public List<Utilizador> GetUsers()
        {
            return _context.Utilizador.ToList();
        }


        // Um utilizador concreto por id 
        // GET: api/MeeeT/getUser/id
        [Route("getUser/{id:int}")]
        [HttpGet]
        public Utilizador GetUser(int id)
        {
            return _context.Utilizador.Find(id);
        }

        //GET: api/meeet/getUserByName
        [Route("getUserByName/{username}")]
        [HttpGet]
        public Utilizador GetUserByName(string username)
        {
            foreach(Utilizador u in _context.Utilizador)
            {
                if (u.Username == username) return u;
            }
            return null;
        }


        // Login
        // GET: api/MeeeT/Login/user/password
        [Route("Login/{username}/{password}")]
        [HttpGet]
        public int Login(string username, string password)
        {
            foreach (var t in _context.Utilizador)
            {
                if (t.Username == username && t.Password == password) return t.Id;
            }
            return -1; //-1 = não encontrou nao tenho paciencia para melhorar
        }

        // Grupo por id
        // GET: api/MeeeT/getGrupo
        [Route("getGrupo/{id_grupo:int}")]
        [HttpGet]
        public Grupo getGrupo(int id_grupo)
        {
            return _context.Grupo.Find(id_grupo);
        }

        // Eventos na database
        // GET: api/MeeeT/getEventos
        [Route("getEventos")]
        [HttpGet]
        public List<Evento> GetEventos()
        {
            return _context.Evento.ToList();
        }

        // Encontra um grupo
        // GET: api/MeeeT/5
        [HttpGet("{id}", Name = "Get")]
        public Grupo Get(int id)
        {
            return _context.Grupo.Find(id);
        }

        // NEW FUNC
        // Amigos de um user
        // GET:
        [Route("getAmigosUser/{id_user:int}")]
        [HttpGet]
        public List<Amigos> GetAmigosUser(int id_user)
        {
            List<Amigos> amigos = new List<Amigos>();
            foreach (var t in _context.Amigos)
            {
                if (id_user == t.IdUser1) amigos.Add(t);
                else if (id_user == t.IdUser2) amigos.Add(t);
            }
            return amigos;
        }

        // NEW FUNC
        // Amigos de um user
        // GET:
        [Route("getAmizadesUser/{id_user:int}")]
        [HttpGet]
        public List<int> GetAmizadesUser(int id_user)
        {
            List<int> amigos = new List<int>();
            foreach(var t in _context.Amigos)
            {
                if (id_user == t.IdUser1) amigos.Add(t.IdUser2);
                else if (id_user == t.IdUser2) amigos.Add(t.IdUser1);
            }
            return amigos;
        }

        // GET:
        [Route("getSaoAmigos/{id_user_global:int}/{id_user_friend:int}")]
        [HttpGet]
        public bool GetSaoAmigos(int id_user_global,int id_user_friend)
        {
            foreach (var t in _context.Amigos)
            {
                if (id_user_global == t.IdUser1 && id_user_friend == t.IdUser2) return true;
                else if (id_user_global == t.IdUser2 && id_user_friend == t.IdUser1) return true;
            }
            return false;
        }

        // Utilizadores por lista de id
        // POST:
        [Route("getUsersPerIDs")]
        [HttpPost]
        public List<Utilizador> GetUsersPerIDs([FromBody] List<int> li)
        {
            List<Utilizador> users = new List<Utilizador>();
            foreach(int i in li) {
                foreach (Utilizador u in _context.Utilizador)
                {
                    if (i == u.Id) users.Add(u);
                }
            }
            
            return users;
        }       

        // Utilizadores por lista de id
        // POST:
        [Route("getEventsPerIDs")]
        [HttpPost]
        public List<Evento> GetEventsPerIDs([FromBody] List<int> li)
        {
            List<Evento> events = new List<Evento>();
            foreach (int i in li)
            {
                foreach (Evento e in _context.Evento)
                {
                    if (i == e.Id) events.Add(e);
                }
            }

            return events;
        }

        // Longitude de um user
        // GET
        [Route("getLongitudeUser/{id_user:int}")]
        [HttpGet]
        public float? GetLongitudeUser (int id_user)
        {
            Utilizador u = GetUser(id_user);
            return u.Longitude;
        }

        // Latitude de um user
        // GET
        [Route("getLatitudeUser/{id_user:int}")]
        [HttpGet]
        public float? GetLatitudeUser (int id_user)
        {
            Utilizador u = GetUser(id_user);
            return u.Latitude;
        }

        // Procura Evento por id
        // GET
        [Route("getEvento/{id_evento:int}")]
        [HttpGet]
        public Evento GetEvento(int id_evento)
        {
            return _context.Evento.Find(id_evento);
        }

        // POR TESTAR
        // UtilizadorEventos de um Evento
        // GET
        [Route("getUserOpcaoPerUser/{id_user:int}")]
        [HttpGet]
        public List<UtilizadorOpcao> GetUserOpcaoPerUser(int id_user)
        {
            List<UtilizadorOpcao> luo = new List<UtilizadorOpcao>();
            foreach (var aux in _context.UtilizadorOpcao)
            {
                if (aux.IdUtilizador == id_user) luo.Add(aux);
            }
            return luo;
        }

        // POR TESTAR
        // UtilizadorEventos de um Evento
        // GET
        [Route("getUserEventosPerEvent/{id_event:int}")]
        [HttpGet]
        public List<UtilizadorEvento> GetUserEventosPerEvent(int id_event)
        {
            List<UtilizadorEvento> lue = new List<UtilizadorEvento>();
            foreach (var aux in _context.UtilizadorEvento)
            {
                if (aux.IdEvento == id_event) lue.Add(aux);
            }
            return lue;
        }

        // POR TESTAR
        // Users num evento
        // GET
        [Route("getUsersPerEvent")]
        [HttpPost]
        public List<Utilizador> GetUsersPerEvent([FromBody] List<UtilizadorEvento> lue)
        {
            List<Utilizador> ret = new List<Utilizador>();
            foreach(var aux in lue)
            {
                foreach (var u in _context.Utilizador)
                {
                    if (u.Id == aux.IdUtilizador) {
                        ret.Add(u);
                        break;
                    }
                }
            }
            return ret;
        }

        // UtilizadorEventos de um Evento
        // GET
        [Route("getAmigosNotEvent/{id_user:int}")]
        [HttpPost]
        public List<Utilizador> GetAmigosNotEvent([FromBody] List<List<Utilizador>> llu)
        {
            List<Utilizador> amigos = llu[0];
            List<Utilizador> uEvento = llu[1];
            List<Utilizador> ret = new List<Utilizador>();
            bool flag = true;
            foreach(Utilizador a in amigos)
            {
                foreach (Utilizador u in uEvento)
                {
                    if (u.Id == a.Id){
                        flag = false;
                    }
                    if (!flag) break;
                }
                if (flag) ret.Add(a);
                flag = true;
            }
            return ret;
        }

        // POR TESTAR
        // UtilizadorEventos de um User
        // GET
        [Route("getUserEventosPerUser/{id_user:int}")]
        [HttpGet]
        public List<UtilizadorEvento> GetUserEventosPerUser(int id_user)
        {
            List<UtilizadorEvento> lue = new List<UtilizadorEvento>();
            foreach (var aux in _context.UtilizadorEvento)
            {
                if (aux.IdUtilizador == id_user) lue.Add(aux);
            }
            return lue;
        }


        // POR TESTAR
        // Eventos num User
        // GET
        [Route("getEventosPerUser")]
        [HttpPost]
        public List<Evento> GetEventosPerUser([FromBody] List<UtilizadorEvento> lue)
        {
            List<Evento> ret = new List<Evento>();
            foreach (UtilizadorEvento ue in lue)
            {
                foreach (var e in _context.Evento)
                {
                    if (e.Id == ue.IdEvento)
                    {
                        ret.Add(e);
                        break;
                    }
                }
            }
            return ret;
        }

        // Eventos num User
        // GET
        [Route("getEventosPerAdmin")]
        [HttpPost]
        public List<Evento> GetEventosPerAdmin([FromBody] List<UtilizadorEvento> lue)
        {
            List<Evento> ret = new List<Evento>();
            foreach (UtilizadorEvento ue in lue)
            {
                foreach (var e in _context.Evento)
                {
                    if (e.Id == ue.IdEvento && e.IdAdmin == ue.IdUtilizador)
                    {
                        ret.Add(e);
                        break;
                    }
                }
            }
            return ret;
        }

        // POR TESTAR
        // UtilizadorGrupos de um User
        // GET
        [Route("getUserGruposPerUser/{id_user:int}")]
        [HttpGet]
        public List<UtilizadorGrupo> GetUserGruposPerUser(int id_user)
        {
            List<UtilizadorGrupo> lug = new List<UtilizadorGrupo>();
            foreach (var aux in _context.UtilizadorGrupo)
            {
                if (aux.IdUtilizador == id_user) lug.Add(aux);
            }
            return lug;
        }

        // Grupos de um utilizador
        // GET: api/MeeeT/getGrupoPerUser
        [Route("getGrupoPerUser")]
        [HttpPost]
        public List<Grupo> GetGrupoPerUser([FromBody] List<UtilizadorGrupo> lug)
        {
            List<Grupo> ret = new List<Grupo>();
            foreach (var x in lug)
            {    
                foreach(var g in _context.Grupo)
                {
                    if(x.IdGrupo == g.Id)
                    {
                        ret.Add(g);
                        break;
                    }
                }
            }
            return ret;
        }

        // UtilizadorGrupos de um User
        // GET
        [Route("getUserGruposPerGroup/{id_group:int}")]
        [HttpGet]
        public List<UtilizadorGrupo> GetUserGruposPerGroup(int id_group)
        {
            List<UtilizadorGrupo> lug = new List<UtilizadorGrupo>();
            foreach (var aux in _context.UtilizadorGrupo)
            {
                if (aux.IdGrupo == id_group) lug.Add(aux);
            }
            return lug;
        }

        // GET: api/MeeeT/getGrupoPerUser
        [Route("getUsersPerGroup")]
        [HttpPost]
        public List<Utilizador> GetUsersPerGroup([FromBody] List<UtilizadorGrupo> lug)
        {
            List<Utilizador> ret = new List<Utilizador>();
            foreach (var x in lug)
            {
                foreach (var g in _context.Utilizador)
                {
                    if (x.IdUtilizador == g.Id)
                    {
                        ret.Add(g);
                        break;
                    }
                }
            }
            return ret;
        }

        // POR TESTAR
        // UtilizadorPedidosAmizade de um User
        // GET
        [Route("getUserPedidosAmizade/{id_user:int}")]
        [HttpGet]
        public List<int> GetUserPedidosAmizade(int id_user)
        {
            List<int> lup = new List<int>();
            foreach (var aux in _context.UtilizadorPedidosAmizade)
            {
                if (aux.IdReceive == id_user) lup.Add(aux.IdSend);
            }
            return lup;
        }

        // UtilizadorPedidosAmizade de um User
        // GET
        [Route("getAllUserPedidosAmizade/{id_user:int}")]
        [HttpGet]
        public List<UtilizadorPedidosAmizade> GetAllUserPedidosAmizade(int id_user)
        {
            List<UtilizadorPedidosAmizade> lup = new List<UtilizadorPedidosAmizade>();
            foreach (var aux in _context.UtilizadorPedidosAmizade)
            {
                if (aux.IdReceive == id_user) lup.Add(aux);
            }
            return lup;
        }

        // UtilizadorPedidosAmizade de um User
        // GET
        [Route("getAllUserConvites/{id_user:int}")]
        [HttpGet]
        public List<UtilizadorConvites> GetAllUserConvites(int id_user)
        {
            List<UtilizadorConvites> luc = new List<UtilizadorConvites>();
            foreach (var aux in _context.UtilizadorConvites)
            {
                if (aux.IdUser == id_user) luc.Add(aux);
            }
            return luc;
        }

        // POR TESTAR
        // EventoRequests de um Evento
        // GET
        [Route("getEventoHasRequests/{id_event:int}")]
        [HttpGet]
        public List<EventoHasRequests> GetEventoHasRequests(int id_event)
        {
            List<EventoHasRequests> ler = new List<EventoHasRequests>();
            foreach (var aux in _context.EventoHasRequests)
            {
                if (aux.IdEvento == id_event) ler.Add(aux);
            }
            return ler;
        }

        // POR TESTAR
        // Requests dum evento
        // GET
        [Route("getEventoRequests")]
        [HttpPost]
        public List<RequestEvento> GetEventoRequests([FromBody] List<EventoHasRequests> lup)
        {
            List<RequestEvento> ret = new List<RequestEvento>();
            foreach (var aux in lup)
            {
                foreach (var u in _context.RequestEvento)
                {
                    if (u.IdUserRequest == aux.IdUserRequest)
                    {
                        ret.Add(u);
                        break;
                    }
                }
            }
            return ret;
        }

        // GET
        [Route("getOpcoesPerUserVotacao/{id_user:int}/{id_votacao:int}")]
        [HttpGet]
        public List<int> GetOpcoesPerUserVotacao(int id_user, int id_votacao)
        {
            List<int> lue = new List<int>();
            foreach (var aux in _context.UtilizadorOpcao)
            {
                if (aux.IdUtilizador == id_user && aux.IdVotacao == id_votacao) lue.Add(aux.IdOpcao);
            }
            return lue;
        }

        // GET
        [Route("getOpcoesPerIDs")]
        [HttpPost]
        public List<Opcao> GetOpcoesPerIDs([FromBody] List<int> opcoes)
        {
            List<Opcao> lue = new List<Opcao>();
            foreach(int i in opcoes)
            {
                foreach (var aux in _context.Opcao)
                {
                    if (aux.IdOpcao == i) lue.Add(aux);
                }
            }
            
            return lue;
        }

        // GET
        [Route("getUserIDsPerOpcao/{id_opcao:int}")]
        [HttpGet]
        public List<int> GetUserIDsPerOpcao(int id_opcao)
        {
            List<int> lue = new List<int>();
            foreach (var aux in _context.UtilizadorOpcao)
            {
                if (aux.IdOpcao == id_opcao) lue.Add(aux.IdUtilizador);
            }
            return lue;
        }

        // UtilizadorEventos de um Evento
        // GET
        [Route("getUserOpcaoPerEvent/{id_event:int}")]
        [HttpGet]
        public List<UtilizadorOpcao> GetUserOpcaoPerEvent(int id_event)
        {
            List<UtilizadorOpcao> lue = new List<UtilizadorOpcao>();
            foreach (var aux in _context.UtilizadorOpcao)
            {
                if (aux.IdEvento == id_event) lue.Add(aux);
            }
            return lue;
        }

        // UtilizadorEventos de um Evento
        // GET
        [Route("getOpcaoPerEvent/{id_event:int}")]
        [HttpGet]
        public List<Opcao> GetOpcaoPerEvent(int id_event)
        {
            List<Opcao> lue = new List<Opcao>();
            foreach (var aux in _context.Opcao)
            {
                if (aux.IdEvento == id_event) lue.Add(aux);
            }
            return lue;
        }

        // GET
        [Route("getOpcaoPerEventVotacao/{id_event:int}/{id_votacao:int}")]
        [HttpGet]
        public List<Opcao> GetOpcaoPerEventVotacao(int id_event, int id_votacao)
        {
            List<Opcao> lue = new List<Opcao>();
            foreach (var aux in _context.Opcao)
            {
                if (aux.IdEvento == id_event && aux.IdVotacao == id_votacao) lue.Add(aux);
            }
            return lue;
        }

        // UtilizadorEventos de um Evento
        // GET
        [Route("getVotacaoPerEvent/{id_event:int}")]
        [HttpGet]
        public List<Votacao> GetVotacaoPerEvent(int id_event)
        {
            List<Votacao> lue = new List<Votacao>();
            foreach (var aux in _context.Votacao)
            {
                if (aux.IdEvento == id_event) lue.Add(aux);
            }
            return lue;
        }

        // GET
        [Route("getSharing/{id_user:int}/{id_event:int}")]
        [HttpGet]
        public int GetSharing(int id_user, int id_event)
        {
            foreach (var t in _context.UtilizadorEvento)
            {
                if (t.IdEvento == id_event && t.IdUtilizador == id_user)
                {
                    return t.SharingPosition;
                }
            }
            return 0;
        }

        /********\
       | * POST * | ---------------------------------------------------------------------------------------------------------------
        \********/

        // POST: api/MeeeT/postamigo
        [Route("ToggleSharing/{id_user:int}/{id_event:int}")]
        [HttpPost]
        public void ToggleSharing(int id_user, int id_event)
        {
            foreach (var t in _context.UtilizadorEvento)
            {
                if (t.IdEvento == id_event && t.IdUtilizador == id_user)
                {
                    if (t.SharingPosition == 0) t.SharingPosition = 1;
                    else t.SharingPosition = 0;
                    break;
                }
            }
            
            _context.SaveChanges();
        }


        // POR TESTAR
        // Insere um amigo
        // POST: api/MeeeT/postamigo
        [Route("PostAmigos")]
        [HttpPost]
        public void PostAmigos([FromBody] Amigos a)
        {
            _context.Amigos.Add(a);
            _context.SaveChanges();
        }

        // POR TESTAR
        // Cria convite e adiciona-o à data base
        // POST
        [Route("MakeConvite")]
        [HttpPost]
        public void MakeConvite ([FromBody] Convites c)
        { 
            _context.Convites.Add(c);
            _context.SaveChanges();
        }

        // POR TESTAR
        // Usa convite para convidar um user com o id
        // POST
        [Route("InviteToEvent/{id:int}")]
        [HttpPost]
        public void InviteToEvent([FromBody] Convites c, int id)
        {
            UtilizadorConvites uc = new UtilizadorConvites();
            uc.IdConvidador = c.IdConvidador;
            uc.IdUser = id;
            uc.IdEvento = c.IdEvento;
            _context.UtilizadorConvites.Add(uc);
            _context.SaveChanges();
        }

        // POR TESTAR
        // Adiciona um evento
        // POST: api/MeeeT/postevento
        [Route("PostEvento")]
        [HttpPost]
        public Evento PostEvento([FromBody] Evento e)
        {
            _context.Evento.Add(e);
            _context.SaveChanges();
            return e;
        }

        // POR TESTAR
        // Convida um user para um certo evento com o seu id
        // POST: api/MeeeT/postevento
        [Route("AddToEvent/{id:int}")]
        [HttpPost]
        public void AddToEvent([FromBody] Evento e,int id)
        {
            UtilizadorEvento ev = new UtilizadorEvento();

            ev.IdEvento = e.Id;
            ev.IdUtilizador = id;
            ev.SharingPosition = 0; //default sharing position é 0 == not sharing
            _context.UtilizadorEvento.Add(ev);
            _context.SaveChanges();
        }

        // POR TESTAR
        // Cria requestevento e adiciona-o à data base
        // POST
        [Route("PostRequestEvento/{id_evento:int}")]
        [HttpPost]
        public void PostRequestEvento ([FromBody] RequestEvento re, int id_evento)
        {
            EventoHasRequests ehr = new EventoHasRequests();
            ehr.IdUserRequest = re.IdUserRequest;
            ehr.IdEvento = id_evento;
            _context.EventoHasRequests.Add(ehr);
            _context.RequestEvento.Add(re);
            _context.SaveChanges();
        }

        // POR TESTAR
        // Cria pedido de amizade e adiciona-o à data base
        // POST
        [Route("PostPedidoAmizade/{id_user_receive:int}")]
        [HttpPost]
        public void PostPedidoAmizade ([FromBody] PedidosAmizade pe, int id_user_receive)
        {
            UtilizadorPedidosAmizade upa = new UtilizadorPedidosAmizade();
            upa.IdSend = pe.IdUserSend;
            upa.IdReceive = id_user_receive;
            _context.UtilizadorPedidosAmizade.Add(upa);
            if(_context.PedidosAmizade.Find(pe.IdUserSend) == null)
            {
                _context.PedidosAmizade.Add(pe);
            }
            _context.SaveChanges();
        }

        // Set na latitude e longitude de um user
        // POST: api/MeeeT/setLatLon
        [Route("SetLatLon/{id:int}/{lat:float}/{lon:float}")]
        [HttpPost]
        public void SetLatLon(int id, float lat, float lon)
        {
            foreach(Utilizador u in _context.Utilizador)
            {
                if(id == u.Id)
                {
                    u.Latitude = lat;
                    u.Longitude = lon;
                }
            }
            _context.SaveChanges();
        }

        // POR TESTAR
        // Adiciona uma opção
        // POST: 
        [Route("PostOpcao")]
        [HttpPost]
        public Opcao PostOpcao ([FromBody] Opcao o)
        {
            _context.Opcao.Add(o);
            _context.SaveChanges();
            return o;
        }

        // POR TESTAR
        // Adiciona uma pessoa a opção
        // POST: 
        [Route("UserChooseOption/{id_user:int}")]
        [HttpPost]
        public void UserChooseOption([FromBody] Opcao o, int id_user)
        {
            UtilizadorOpcao uo = new UtilizadorOpcao();
            uo.IdUtilizador = id_user;
            uo.IdOpcao = o.IdOpcao;
            uo.IdVotacao = o.IdVotacao;
            uo.IdEvento = o.IdEvento;
            _context.UtilizadorOpcao.Add(uo);
            _context.SaveChanges();
        }


        // POR TESTAR
        // Adiciona uma votação
        // POST: api/MeeeT/postvotacao
        [Route("PostVotacao")]
        [HttpPost]
        public Votacao PostVotacao([FromBody] Votacao v)
        {
            _context.Votacao.Add(v);
            _context.SaveChanges();
            return v;
        }
        

        // POR TESTAR
        // Cria grupo e adiciona-o à data base
        // POST
        [Route("PostGrupo")]
        [HttpPost]
        public Grupo PostGrupo ([FromBody] Grupo g)
        {
            _context.Grupo.Add(g);
            _context.SaveChanges();
            return g;
        }

        // POR TESTAR
        // Adiciona pessoa a grupo e adiciona-o à data base
        // POST
        [Route("AddToGroup/{id:int}")]
        [HttpPost]
        public void AddToGroup([FromBody] Grupo g, int id)
        {
            UtilizadorGrupo ug = new UtilizadorGrupo();
            ug.IdGrupo = g.Id;
            ug.IdUtilizador = id;
            _context.UtilizadorGrupo.Add(ug);
            _context.SaveChanges();
        }


        // POR TESTAR
        // Adiciona um utilizador
        // POST: api/MeeeT/postuser
        [Route("PostUser")]
        [HttpPost]
        public Utilizador PostUser([FromBody] Utilizador u)
        {
            _context.Utilizador.Add(u);
            _context.SaveChanges();
            return u;
        }


        /*******\
       | * PUT * | ---------------------------------------------------------------------------------------------------------------
        \*******/

        // PUT: api/MeeeT/5
        [Route("UpdateUser/{id:int}")]
        [HttpPut]
        public void UpdateUser([FromBody] Utilizador u, int id)
        {
            Utilizador user = GetUser(id);
            if(user != null) {
                user.Username = u.Username;
                user.Email = u.Email;
                user.Password = u.Password;
                user.Longitude = u.Longitude;
                user.Latitude = u.Latitude;
                user.UrlFoto = u.UrlFoto;
                user.Morada = u.Morada;
                user.DataNascimento = u.DataNascimento;
                user.Genero = u.Genero;
                user.Bio = u.Bio;
                _context.SaveChanges();
            } 
        }

        // PUT: api/MeeeT/5
        [Route("UpdateEvent/{id:int}")]
        [HttpPut]
        public void UpdateEvent([FromBody] Evento e, int id)
        {
            Evento evento = GetEvento(id);
            if (evento != null)
            {
                evento.Nome = e.Nome;
                evento.DataHora = e.DataHora;
                evento.Longitude = e.Longitude;
                evento.Latitude = e.Latitude;
                evento.TipoEvento = e.TipoEvento;
                evento.IdAdmin = e.IdAdmin;
                evento.Descricao = e.Descricao;
                evento.IdadeMinima = e.IdadeMinima;
                _context.SaveChanges();
            }
        }


        /**********\
       | * DELETE * | ---------------------------------------------------------------------------------------------------------------
        \**********/

        // DELETE: api/ApiWithActions/5
        [Route("DeleteUser")]
        [HttpDelete]
        public void DeleteUser([FromBody] Utilizador u)
        {
            _context.Utilizador.Remove(u);
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteAmigos")]
        [HttpDelete]
        public void DeleteAmigos([FromBody] List<Amigos> la)
        {
            foreach (Amigos a in la)
            {
                _context.Amigos.Remove(a);
            }
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteAmizade/{id_1:int}/{id_2:int}")]
        [HttpDelete]
        public void DeleteAmizade(int id_1, int id_2)
        {
            foreach(Amigos a in _context.Amigos)
            {
                if (a.IdUser1 == id_1 && a.IdUser2 == id_2) { 
                    _context.Amigos.Remove(a);
                    break;
                }
                else if (a.IdUser1 == id_2 && a.IdUser2 == id_1) { 
                    _context.Amigos.Remove(a);
                    break;
                }
            }
            
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteUserGrupos")]
        [HttpDelete]
        public void DeleteUserGrupos([FromBody] List<UtilizadorGrupo> lug)
        {
            foreach (UtilizadorGrupo ug in lug)
            {
                _context.UtilizadorGrupo.Remove(ug);
            }
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteGrupo")]
        [HttpDelete]
        public void DeleteGrupo([FromBody] Grupo g)
        {
            _context.Grupo.Remove(g);
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteUserEventos")]
        [HttpDelete]
        public void DeleteUserEventos([FromBody] List<UtilizadorEvento> lue)
        {
            foreach(UtilizadorEvento ue in lue)
            {
                _context.UtilizadorEvento.Remove(ue);
            }
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteEventoRequests")]
        [HttpDelete]
        public void DeleteEventoRequests([FromBody] List<EventoHasRequests> ler)
        {
            foreach (EventoHasRequests er in ler)
            {
                _context.EventoHasRequests.Remove(er);
            }
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteEvento")]
        [HttpDelete]
        public void DeleteEvento([FromBody] Evento e)
        {
            _context.Evento.Remove(e);
            _context.SaveChanges();
        }

        [Route("DeleteEventosAdmin")]
        [HttpDelete]
        public void DeleteEventosAdmin([FromBody] List<Evento> le)
        {
            foreach (Evento e in le)
            {
                _context.Evento.Remove(e);
            }
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteVotacao")]
        [HttpDelete]
        public void DeleteVotacao([FromBody] List<Votacao> lv)
        {
            foreach (Votacao v in lv)
            {
                _context.Votacao.Remove(v);
            }
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteOpcao")]
        [HttpDelete]
        public void DeleteOpcao([FromBody] List<Opcao> lo)
        {
            foreach (Opcao o in lo)
            {
                _context.Opcao.Remove(o);
            }
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteUserOpcao")]
        [HttpDelete]
        public void DeleteUserOpcao([FromBody] List<UtilizadorOpcao> luo)
        {
            foreach (UtilizadorOpcao uo in luo)
            {
                _context.UtilizadorOpcao.Remove(uo);
            }
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteSingleUserOpcao/{id_user:int}/{id_opcao:int}")]
        [HttpDelete]
        public void DeleteSingleUserOpcao(int id_user, int id_opcao)
        {
            foreach (var aux in _context.UtilizadorOpcao)
            {
                if (aux.IdUtilizador == id_user && aux.IdOpcao == id_opcao) _context.UtilizadorOpcao.Remove(aux);
            }     
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteUserConvites")]
        [HttpDelete]
        public void DeleteUserConvites([FromBody] List<UtilizadorConvites> luc)
        {
            foreach (UtilizadorConvites uc in luc)
            {
                _context.UtilizadorConvites.Remove(uc);
            }
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteUserSingleConvite")]
        [HttpDelete]
        public void DeleteUserSingleConvite([FromBody] UtilizadorConvites uc)
        {
            _context.UtilizadorConvites.Remove(uc);
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteUserPedidosAmizade")]
        [HttpDelete]
        public void DeleteUserPedidosAmizade([FromBody] UtilizadorPedidosAmizade upa)
        {
            _context.UtilizadorPedidosAmizade.Remove(upa);
            _context.SaveChanges();
        }

        // DELETE: api/ApiWithActions/5
        [Route("DeleteAllUserPedidosAmizade")]
        [HttpDelete]
        public void DeleteAllUserPedidosAmizade([FromBody] List<UtilizadorPedidosAmizade> lup)
        {
            foreach (UtilizadorPedidosAmizade up in lup)
            {
                _context.UtilizadorPedidosAmizade.Remove(up);
            }
            _context.SaveChanges();
        }
    }
}
