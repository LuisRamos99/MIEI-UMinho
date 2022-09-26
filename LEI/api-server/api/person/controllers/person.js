const { parseMultipartData, sanitizeEntity } = require('strapi-utils');

module.exports = {

  async updateSpecial(ctx) {
    const { id } = ctx.params;
    let entity;
    entity = await strapi.services.person.update({ id }, {$push : {memories: ctx.request.body.idMemory}});
    return sanitizeEntity(entity, { model: strapi.models.person });
  },
  
};