import { useState, useEffect } from 'react';
import { departmentApi } from '../services/api';

export default function Departments() {
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [modalOpen, setModalOpen] = useState(false);
  const [editingDept, setEditingDept] = useState(null);
  const [formData, setFormData] = useState({ deptName: '', location: '' });

  const fetchDepartments = async () => {
    try {
      setLoading(true);
      const { data } = await departmentApi.getAll();
      setDepartments(data || []);
      setError('');
    } catch (err) {
      setError(err.response?.data?.message || err.message || 'Failed to fetch departments');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDepartments();
  }, []);

  const openCreateModal = () => {
    setEditingDept(null);
    setFormData({ deptName: '', location: '' });
    setModalOpen(true);
  };

  const openEditModal = (dept) => {
    setEditingDept(dept);
    setFormData({
      deptName: dept.deptName || '',
      location: dept.location || '',
    });
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
    setEditingDept(null);
    setFormData({ deptName: '', location: '' });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      if (editingDept) {
        await departmentApi.update(editingDept.deptId, formData);
      } else {
        await departmentApi.create(formData);
      }
      closeModal();
      fetchDepartments();
    } catch (err) {
      setError(err.response?.data?.message || err.message || 'Operation failed');
    }
  };

  const handleDelete = async (deptId) => {
    if (!window.confirm('Are you sure you want to delete this department?')) return;
    try {
      await departmentApi.delete(deptId);
      fetchDepartments();
    } catch (err) {
      setError(err.response?.data?.message || err.message || 'Delete failed');
    }
  };

  if (loading) {
    return (
      <div className="page">
        <div className="spinner" />
        <p>Loading departments...</p>
      </div>
    );
  }

  return (
    <div className="page">
      <div className="page-header">
        <h1>Departments</h1>
        <button type="button" onClick={openCreateModal} className="btn btn-primary">
          Add Department
        </button>
      </div>
      {error && <div className="alert alert-error">{error}</div>}
      <div className="table-container">
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Location</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {departments.length === 0 ? (
              <tr>
                <td colSpan={4}>No departments found</td>
              </tr>
            ) : (
              departments.map((dept) => (
                <tr key={dept.deptId}>
                  <td>{dept.deptId}</td>
                  <td>{dept.deptName}</td>
                  <td>{dept.location || '-'}</td>
                  <td>
                    <button
                      type="button"
                      onClick={() => openEditModal(dept)}
                      className="btn btn-sm btn-outline"
                    >
                      Edit
                    </button>
                    <button
                      type="button"
                      onClick={() => handleDelete(dept.deptId)}
                      className="btn btn-sm btn-danger"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {modalOpen && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h2>{editingDept ? 'Edit Department' : 'Add Department'}</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="deptName">Department Name *</label>
                <input
                  id="deptName"
                  value={formData.deptName}
                  onChange={(e) => setFormData({ ...formData, deptName: e.target.value })}
                  required
                />
              </div>
              <div className="form-group">
                <label htmlFor="location">Location</label>
                <input
                  id="location"
                  value={formData.location}
                  onChange={(e) => setFormData({ ...formData, location: e.target.value })}
                />
              </div>
              <div className="modal-actions">
                <button type="button" onClick={closeModal} className="btn btn-outline">
                  Cancel
                </button>
                <button type="submit" className="btn btn-primary">
                  {editingDept ? 'Update' : 'Create'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
